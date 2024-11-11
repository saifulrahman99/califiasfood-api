package com.rahmandev.califiasfood.repository;

import com.rahmandev.califiasfood.entity.GlobalDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalDiscountRepository extends JpaRepository<GlobalDiscount, String> {
}
