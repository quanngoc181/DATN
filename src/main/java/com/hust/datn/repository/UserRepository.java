package com.hust.datn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hust.datn.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>, CustomUserRepository {

}
