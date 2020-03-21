package com.hust.datn.repositoryimpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import com.hust.datn.entity.Account;
import com.hust.datn.repository.AccountRepositoryCustom;

@Transactional
public class AccountRepositoryImpl implements AccountRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;

	public AccountRepositoryImpl() {

	}

	public Account getByUsername(String username) {
		String hql = "FROM Account A WHERE A.username = :username";

		Account account = (Account) entityManager.createQuery(hql).setParameter("username", username).getSingleResult();

		return account;
	}
}
