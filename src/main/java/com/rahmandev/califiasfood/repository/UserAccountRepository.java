package com.rahmandev.califiasfood.repository;

import com.rahmandev.califiasfood.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
}
