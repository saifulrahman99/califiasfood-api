package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.update.UpdateAddressRequest;
import com.rahmandev.califiasfood.dto.response.AddressResponse;
import com.rahmandev.califiasfood.entity.Address;
import com.rahmandev.califiasfood.repository.AddressRepository;
import com.rahmandev.califiasfood.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Override
    public Address findAddressById(String id) {
        return addressRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AddressResponse create(Address address) {
        Address saveAddress = addressRepository.saveAndFlush(address);
        return AddressResponse.builder()
                .id(saveAddress.getId())
                .address(saveAddress.getAddress())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AddressResponse update(UpdateAddressRequest address) {
        Address currentAddress = findAddressById(address.getId());
        currentAddress.setAddress(address.getAddress());
        return AddressResponse.builder()
                .id(currentAddress.getId())
                .address(currentAddress.getAddress())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        Address address = findAddressById(id);
        addressRepository.delete(address);
    }
}
