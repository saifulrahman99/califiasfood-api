package com.rahmandev.califiasfood.service;

import com.rahmandev.califiasfood.entity.BillDetailTopping;

import java.util.List;

public interface BillDetailToppingService {
    List<BillDetailTopping> createBulk(List<BillDetailTopping> billDetailToppings);
}
