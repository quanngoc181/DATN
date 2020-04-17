package com.hust.datn.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
		
		System.out.println(draw);
		System.out.println(start);
		System.out.println(length);
		System.out.println(value);
		System.out.println(dir);
		System.out.println(order);
		System.out.println(col);
		
		int countAll = (int) userRepository.count();
		int countFiltered = userRepository.countFiltered(value);
		List<Users> data = userRepository.datatable(value, dir, start, length, col);

		List<Users> users = new ArrayList<>();
		users.add(new Users("test1", "pass1", true));
		users.add(new Users("test2", "pass2", false));
		return new DatatableDTO<Users>(draw, countFiltered, countAll, data);
	}
}
