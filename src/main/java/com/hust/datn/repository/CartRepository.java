package com.hust.datn.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.datn.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, UUID> {
	List<Cart> findByUserId(UUID id);
}
