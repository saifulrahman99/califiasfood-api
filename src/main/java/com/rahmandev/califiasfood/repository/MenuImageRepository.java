package com.rahmandev.califiasfood.repository;

import com.rahmandev.califiasfood.entity.MenuImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuImageRepository extends JpaRepository<MenuImage, String> {
    @Query("SELECT i FROM MenuImage i WHERE i.menu.id = :menuId")
    List<MenuImage> findByMenuId(String menuId);
}
