package com.hust.datn.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.datn.entity.DiscountProduct;

public interface DiscountProductRepository extends JpaRepository<DiscountProduct, UUID> {
	int countByDescriptionContains(String key);
	List<DiscountProduct> findByDescriptionContains(String key, Pageable pageable);
}
