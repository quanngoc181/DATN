package com.hust.datn.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.datn.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
	int count(Specification<Category> spec);
	List<Category> findAll(Specification<Category> spec, Pageable pageable);
}
