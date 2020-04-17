package com.hust.datn.repository;

import org.springframework.stereotype.Repository;

import com.hust.datn.entity.Account;

@Repository
public interface CustomAccountRepository {
	Account getByUsername(String username);
}
