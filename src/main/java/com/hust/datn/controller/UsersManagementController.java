package com.hust.datn.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hust.datn.dto.DatatableDTO;
import com.hust.datn.dto.UsersDetailDTO;
import com.hust.datn.entity.Account;
import com.hust.datn.entity.ReceiveAddress;
import com.hust.datn.entity.Users;
import com.hust.datn.repository.AccountRepository;
import com.hust.datn.repository.UserRepository;
import com.hust.datn.service.UsersService;

@Controller
public class UsersManagementController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UsersService usersService;
	
	@Autowired
	UserDetailsManager userDetailsManager;
	
	@Autowired
	AccountRepository accountRepository;
	
	@GetMapping("/admin/users-management")
	public String index() {
		return "admin/users-management";
	}

	@GetMapping("/admin/users-management/datatable")
	@ResponseBody
	public DatatableDTO<UsersDetailDTO> datatable(HttpServletRequest request) {
		int draw = Integer.parseInt(request.getParameter("draw"));
		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		String value = request.getParameter("search[value]");
		String dir = request.getParameter("order[0][dir]");
		String order = request.getParameter("order[0][column]");
		String col = request.getParameter("columns[" + order + "][name]");
		
		Sort sort;
		if(dir.equalsIgnoreCase("asc"))
			sort = Sort.by(Sort.Direction.ASC, col);
		else
			sort = Sort.by(Sort.Direction.DESC, col);
		
		int countAll = (int) userRepository.count();
		int countFiltered = userRepository.countByUsernameContains(value);
		List<Users> users = userRepository.findByUsernameContains(value, PageRequest.of(start/length, length, sort));
		
		List<UsersDetailDTO> data = usersService.createUsersDetail(users);

		return new DatatableDTO<UsersDetailDTO>(draw, countAll, countFiltered, data);
	}
	
	@PostMapping("/admin/users-management/change-status")
	@ResponseBody
	public void changeStatus(String username, boolean status) {
		UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
		UserDetails user = User.builder().username(userDetails.getUsername())
				.password(userDetails.getPassword()).roles("USER").disabled(!status).build();
		
		userDetailsManager.updateUser(user);
	}
	
	@GetMapping("/admin/users-management/view-detail")
	@ResponseBody
	public ModelAndView viewDetail(String username) {
		Map<String, Object> map = new HashMap<>();
		
		Account account = accountRepository.findByUsername(username);
		map.put("account", account);
		
		Set<ReceiveAddress> reList = account.getReceiveAddresses();
		map.put("reList", reList);
		
		return new ModelAndView("partial/view-user-detail", map);
	}
}
