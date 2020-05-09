package com.hust.datn.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.datn.entity.OptionItem;

public interface ItemRepository extends JpaRepository<OptionItem, UUID> {
	int count(Specification<OptionItem> spec);
	List<OptionItem> findAll(Specification<OptionItem> spec, Pageable pageable);
}
