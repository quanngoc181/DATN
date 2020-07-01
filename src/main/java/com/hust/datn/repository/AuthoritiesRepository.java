package com.hust.datn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.datn.entity.Authorities;
import com.hust.datn.entity.AuthorityId;

public interface AuthoritiesRepository extends JpaRepository<Authorities, AuthorityId> {
	
}
