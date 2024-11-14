package com.rahmandev.califiasfood.repository;

import com.rahmandev.califiasfood.entity.Topping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToppingRepository extends JpaRepository<Topping, String> {

    @Query("SELECT t FROM Topping t WHERE t.deleteAt IS NULL")
    List<Topping> findAllToppings();
}
