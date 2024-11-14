package com.rahmandev.califiasfood.service;

import com.rahmandev.califiasfood.dto.request.search.SearchDiscountRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateDiscountRequest;
import com.rahmandev.califiasfood.dto.response.DiscountResponse;
import com.rahmandev.califiasfood.entity.Discount;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DiscountService {
    DiscountResponse update(UpdateDiscountRequest request);

    Discount getDiscountById(String id);

    DiscountResponse getById(String id);

    Page<DiscountResponse> getAll(SearchDiscountRequest request);

    void updateStatusDiscount(String id);
}
