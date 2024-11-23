package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.entity.BillDetailTopping;
import com.rahmandev.califiasfood.repository.BillDetailToppingRepository;
import com.rahmandev.califiasfood.service.BillDetailToppingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BillDetailToppingServiceImpl implements BillDetailToppingService {
    private final BillDetailToppingRepository billDetailToppingRepository;

    @Override
    public List<BillDetailTopping> createBulk(List<BillDetailTopping> billDetailToppings) {
        return billDetailToppingRepository.saveAllAndFlush(billDetailToppings);
    }
}
