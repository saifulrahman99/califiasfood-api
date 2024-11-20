package com.rahmandev.califiasfood.service;

import com.rahmandev.califiasfood.dto.request.AddressRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateAddressRequest;
import com.rahmandev.califiasfood.dto.response.AddressResponse;
import com.rahmandev.califiasfood.entity.Address;

import java.util.List;

public interface AddressService {
    Address getById(String id);

    AddressResponse findById(String id);

    AddressResponse create(AddressRequest request);

    AddressResponse update(UpdateAddressRequest request);

    void delete(String id);

    List<AddressResponse> getAllByCustomerId(String customerId);
}
