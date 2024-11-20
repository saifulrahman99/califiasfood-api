package com.rahmandev.califiasfood.repository;

import com.rahmandev.califiasfood.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    @Query("SELECT a FROM Address a WHERE a.customer.id = :customerId")
    public List<Address> findAllByCustomerId(@Param("customerId") String id);
}
