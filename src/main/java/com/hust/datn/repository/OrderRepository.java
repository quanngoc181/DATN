package com.hust.datn.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.datn.entity.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {

}
