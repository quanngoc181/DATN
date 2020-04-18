package com.hust.datn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.hust.datn.dto.UsersDetailDTO;
import com.hust.datn.entity.Users;

@Service
public class UsersService {
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserDetailsManager userDetailsManager;
	
	public UsersService() {
	}
	
	public List<UsersDetailDTO> createUsersDetail(List<Users> users) {
		List<UsersDetailDTO> dtos = new ArrayList<>();
		
		for (Users user : users) {
			UsersDetailDTO dto = new UsersDetailDTO();
			dto.username = user.getUsername();
			dto.enabled = user.isEnabled();
			
			UserDetails detail = userDetailsManager.loadUserByUsername(user.getUsername());
			dto.isAdmin = detail.getAuthorities().size() == 2;
			dto.isUser = true;
			
			dtos.add(dto);
		}
		
		return dtos;
	}
}
