package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.AddressRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateAddressRequest;
import com.rahmandev.califiasfood.dto.response.AddressResponse;
import com.rahmandev.califiasfood.entity.Address;
import com.rahmandev.califiasfood.entity.Customer;
import com.rahmandev.califiasfood.repository.AddressRepository;
import com.rahmandev.califiasfood.service.AddressService;
import com.rahmandev.califiasfood.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final CustomerService customerService;

    @Override
    public AddressResponse findById(String id) {
        Address address = getById(id);
        return AddressResponse.builder()
                .id(address.getId())
                .address(address.getAddress())
                .customerId(address.getCustomer().getId())
                .build();
    }

    @Override
    public Address getById(String id) {
        return addressRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AddressResponse create(AddressRequest request) {
        Customer customer = customerService.getCustomerById(request.getCustomerId());
        Address address = addressRepository.saveAndFlush(
                Address.builder()
                        .address(request.getAddress())
                        .customer(customer)
                        .build()
        );
        return AddressResponse.builder()
                .id(address.getId())
                .address(address.getAddress())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AddressResponse update(UpdateAddressRequest address) {
        Address currentAddress = getById(address.getId());
        currentAddress.setAddress(address.getAddress());
        return AddressResponse.builder()
                .id(currentAddress.getId())
                .address(currentAddress.getAddress())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        Address address = getById(id);
        addressRepository.delete(address);
    }

    @Override
    public List<AddressResponse> getAllByCustomerId(String customerId) {
        List<Address> addresses = addressRepository.findAllByCustomerId(customerId);
        return addresses.stream().map(
                address -> {
                    return AddressResponse.builder()
                            .id(address.getId())
                            .address(address.getAddress())
                            .customerId(address.getCustomer().getId())
                            .build();
                }
        ).toList();
    }
}
