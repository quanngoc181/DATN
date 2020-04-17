package com.hust.datn.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hust.datn.entity.Users;

@Repository
public interface CustomUserRepository {
	int countFiltered(String value);
	List<Users> datatable(String value, String dir, int start, int length, String col);
}
