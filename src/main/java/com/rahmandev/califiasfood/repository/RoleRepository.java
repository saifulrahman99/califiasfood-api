package com.rahmandev.califiasfood.repository;

import com.rahmandev.califiasfood.constant.UserRole;
import com.rahmandev.califiasfood.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(UserRole role);
}
