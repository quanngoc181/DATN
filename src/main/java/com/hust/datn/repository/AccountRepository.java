package com.hust.datn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.datn.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	Account findByUsername(String username);
}
