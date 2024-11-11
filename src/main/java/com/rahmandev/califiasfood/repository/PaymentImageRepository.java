package com.rahmandev.califiasfood.repository;

import com.rahmandev.califiasfood.entity.PaymentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentImageRepository extends JpaRepository<PaymentImage, String> {
}
