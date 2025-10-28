package com.example.caremate.user.repository;

import com.example.caremate.framework.model.UserRoles;
import com.example.caremate.user.entity.User;
import com.example.caremate.user.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    long countByRoles(UserRoles role);

    List<User> findTop5ByRolesOrderByCreatedOnDesc(UserRoles roles);

    @Query("SELECT u FROM User u WHERE u.roles = :role ORDER BY u.createdOn DESC")
    List<User> findLatestUsersByRole(@Param("role") UserRoles role, org.springframework.data.domain.Pageable pageable);
}