package com.hust.datn.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.datn.entity.Account;

public interface AccountRepository extends JpaRepository<Account, UUID> {
	Account findByUsername(String username);
}
