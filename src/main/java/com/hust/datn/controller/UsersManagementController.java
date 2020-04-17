package com.hust.datn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hust.datn.dto.DatatableDTO;
import com.hust.datn.entity.Users;
import com.hust.datn.repository.UserRepository;

@Controller
public class UsersManagementController {
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/admin/users-management")
	public String index() {
		return "admin/users-management";
	}

	@GetMapping("/admin/users-management/datatable")
	@ResponseBody
	public DatatableDTO<Users> Datatable(HttpServletRequest request) {
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
		List<Users> data = userRepository.findByUsernameContains(value, PageRequest.of(start/length, length, sort));

		return new DatatableDTO<Users>(draw, countFiltered, countAll, data);
	}
}
