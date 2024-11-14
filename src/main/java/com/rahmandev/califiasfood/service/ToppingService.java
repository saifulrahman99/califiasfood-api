package com.rahmandev.califiasfood.service;

import com.rahmandev.califiasfood.dto.request.ToppingRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateToppingRequest;
import com.rahmandev.califiasfood.dto.response.ToppingResponse;
import com.rahmandev.califiasfood.entity.Topping;

import java.util.List;

public interface ToppingService {
    ToppingResponse create(ToppingRequest request);

    List<ToppingResponse> getAll();

    Topping getToppingById(String id);

    ToppingResponse update(UpdateToppingRequest request);

    void delete(String id);
}
