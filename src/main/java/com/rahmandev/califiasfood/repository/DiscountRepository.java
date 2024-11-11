package com.rahmandev.califiasfood.repository;

import com.rahmandev.califiasfood.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, String> {
}
