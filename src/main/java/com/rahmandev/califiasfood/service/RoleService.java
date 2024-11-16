package com.rahmandev.califiasfood.service;

import com.rahmandev.califiasfood.constant.UserRole;
import com.rahmandev.califiasfood.entity.Role;

public interface RoleService {
    Role getOrSave(UserRole role);
}
