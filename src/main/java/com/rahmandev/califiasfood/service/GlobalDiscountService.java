package com.rahmandev.califiasfood.service;

import com.rahmandev.califiasfood.dto.request.GlobalDiscountRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateGlobalDiscountRequest;
import com.rahmandev.califiasfood.dto.response.GlobalDiscountResponse;
import com.rahmandev.califiasfood.entity.GlobalDiscount;

import java.util.List;

public interface GlobalDiscountService {
    GlobalDiscountResponse create(GlobalDiscountRequest request);

    GlobalDiscountResponse update(UpdateGlobalDiscountRequest request);

    GlobalDiscount getGlobalDiscountById(String id);

    GlobalDiscountResponse getById(String id);

    List<GlobalDiscountResponse> getAll();

    void updateStatusGlobalDiscount(String id);

    void delete(String id);
}
