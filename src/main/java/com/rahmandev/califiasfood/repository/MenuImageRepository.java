package com.rahmandev.califiasfood.repository;

import com.rahmandev.califiasfood.entity.MenuImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuImageRepository extends JpaRepository<MenuImage, String> {
}
