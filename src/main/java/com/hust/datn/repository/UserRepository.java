package com.hust.datn.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.datn.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {
	int countByUsernameContains(String key);
	List<Users> findByUsernameContains(String key, Pageable pageable);
}
