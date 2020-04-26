package com.hust.datn.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.datn.entity.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {
	int count(Specification<Product> spec);
	List<Product> findAll(Specification<Product> spec, Pageable pageable);
}
