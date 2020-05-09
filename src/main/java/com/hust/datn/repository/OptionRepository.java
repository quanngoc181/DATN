package com.hust.datn.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.datn.entity.ProductOption;

public interface OptionRepository extends JpaRepository<ProductOption, UUID> {
	int countByNameContains(String key);
	List<ProductOption> findByNameContains(String key, Pageable pageable);
}
