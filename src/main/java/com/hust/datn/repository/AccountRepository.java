package com.hust.datn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hust.datn.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>, CustomAccountRepository {
	Account findByUsername(String username);
}
