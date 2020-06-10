package com.hust.datn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.datn.entity.Constant;

public interface ConstantRepository extends JpaRepository<Constant, String> {
	
}
