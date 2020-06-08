package com.hust.datn.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.datn.entity.ResetPasswordToken;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, UUID> {
	ResetPasswordToken findByUserId(UUID userId);
	ResetPasswordToken findByToken(UUID token);
}
