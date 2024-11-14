package com.rahmandev.califiasfood.service;

import com.rahmandev.califiasfood.dto.request.update.UpdateDiscountRequest;
import com.rahmandev.califiasfood.dto.response.DiscountResponse;
import com.rahmandev.califiasfood.entity.Discount;

import java.util.List;

public interface DiscountService {
    DiscountResponse updateDiscount(UpdateDiscountRequest request);

    Discount getDiscountById(String id);

    DiscountResponse getById(String id);

    List<DiscountResponse> getAll();

    void updateStatusDiscount(String id);
}
