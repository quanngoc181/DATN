package com.hust.datn.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.datn.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
	List<Notification> findByReceiverOrderByCreateAtDesc(String username);
}
