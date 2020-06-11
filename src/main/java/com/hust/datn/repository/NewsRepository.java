package com.hust.datn.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.datn.entity.News;

public interface NewsRepository extends JpaRepository<News, UUID> {
	int count(Specification<News> spec);
	List<News> findAll(Specification<News> spec, Pageable pageable);
}
