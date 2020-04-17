package com.hust.datn.repositoryimpl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hust.datn.entity.Account;
import com.hust.datn.repository.CustomAccountRepository;

@Transactional
public class CustomAccountRepositoryImpl implements CustomAccountRepository {

	@Autowired
	EntityManager entityManager;

	public CustomAccountRepositoryImpl() {
	}

	@Override
	public Account getByUsername(String username) {
		String hql = "FROM Account A WHERE A.username = :username";

		Account account = (Account) entityManager.createQuery(hql).setParameter("username", username).getSingleResult();

		return account;
	}
}
