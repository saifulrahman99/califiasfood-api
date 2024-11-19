package com.rahmandev.califiasfood.service;

import com.rahmandev.califiasfood.dto.request.update.UpdateAddressRequest;
import com.rahmandev.califiasfood.dto.response.AddressResponse;
import com.rahmandev.califiasfood.entity.Address;

public interface AddressService {
    Address findAddressById(String id);

    AddressResponse create(Address address);

    AddressResponse update(UpdateAddressRequest request);

    void delete(String id);
}
