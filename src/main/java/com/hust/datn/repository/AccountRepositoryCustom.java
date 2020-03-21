package com.hust.datn.repository;

import org.springframework.stereotype.Repository;

import com.hust.datn.entity.Account;

@Repository
public interface AccountRepositoryCustom {
	Account getByUsername(String username);
}
