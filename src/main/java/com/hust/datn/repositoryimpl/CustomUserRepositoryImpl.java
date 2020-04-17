package com.hust.datn.repositoryimpl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hust.datn.entity.Users;
import com.hust.datn.repository.CustomUserRepository;

@Transactional
public class CustomUserRepositoryImpl implements CustomUserRepository {

	@Autowired
	EntityManager entityManager;

	public CustomUserRepositoryImpl() {
	}

	@Override
	public int countFiltered(String value) {
		String hql = "FROM Users A WHERE A.username LIKE :keyword";

		int users = entityManager.createQuery(hql).setParameter("keyword", "%" + value + "%").getResultList().size();

		return users;
	}

	@Override
	public List<Users> datatable(String value, String dir, int start, int length, String col) {
		System.out.println(dir);
		String hql = "FROM Users A WHERE A.username LIKE :keyword ORDER BY A.username :test";

		@SuppressWarnings("unchecked")
		List<Users> users = entityManager.createQuery(hql)
				.setParameter("keyword", "%" + value + "%")
				// .setParameter("col", col)
				.setParameter("test", dir.toUpperCase())
				.setFirstResult(start)
				.setMaxResults(length)
				.getResultList();

		return users;
	}
}
