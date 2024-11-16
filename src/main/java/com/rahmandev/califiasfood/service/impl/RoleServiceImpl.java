package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.constant.UserRole;
import com.rahmandev.califiasfood.entity.Role;
import com.rahmandev.califiasfood.repository.RoleRepository;
import com.rahmandev.califiasfood.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Role getOrSave(UserRole role) {
        return roleRepository.findByRole(role)
                .orElseGet(() -> roleRepository.saveAndFlush(Role.builder().role(role).build()));
    }
}
