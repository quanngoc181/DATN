package com.hust.datn.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hust.datn.entity.ReceiveAddress;

public interface ReceiveAddressRepository extends JpaRepository<ReceiveAddress, UUID> {

}
