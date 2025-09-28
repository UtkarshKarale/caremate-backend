package com.example.hospital_management.user.repository;

import com.example.hospital_management.user.entity.User;
import com.example.hospital_management.user.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    List<User> findAllByStatus(UserStatus status);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);
}
