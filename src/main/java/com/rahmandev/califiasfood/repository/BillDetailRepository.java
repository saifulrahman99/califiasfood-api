package com.rahmandev.califiasfood.repository;

import com.rahmandev.califiasfood.entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, String> {
}
