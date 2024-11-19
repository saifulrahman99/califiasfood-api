package com.rahmandev.califiasfood.service;

import com.rahmandev.califiasfood.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserAccount getByUserId(String id);

    UserAccount getByContext();
}
