package com.rahmandev.califiasfood.repository;

import com.rahmandev.califiasfood.entity.Topping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToppingRepository extends JpaRepository<Topping, String> {
}
