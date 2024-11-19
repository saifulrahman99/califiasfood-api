package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.AddressRequest;
import com.rahmandev.califiasfood.dto.request.CustomerRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateAddressRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateCustomerRequest;
import com.rahmandev.califiasfood.dto.response.AddressResponse;
import com.rahmandev.califiasfood.dto.response.CustomerResponse;
import com.rahmandev.califiasfood.entity.Address;
import com.rahmandev.califiasfood.entity.Customer;
import com.rahmandev.califiasfood.repository.CustomerRepository;
import com.rahmandev.califiasfood.service.AddressService;
import com.rahmandev.califiasfood.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AddressService addressService;

    @Override
    public Customer create(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public Page<CustomerResponse> findAll(CustomerRequest request) {
        return null;
    }

    @Override
    public CustomerResponse findById(String id) {
        Customer customer = getCustomerById(id);
        return getCustomerResponse(customer);
    }

    @Override
    public Customer getCustomerById(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    @Override
    public CustomerResponse findByUserAccountId(String id) {
        Customer customer = customerRepository.findByUserAccountId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
        return getCustomerResponse(customer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerResponse update(UpdateCustomerRequest request) {
        Customer customer = getCustomerById(request.getId());
        customer.setName(request.getName());
        customer.setPhoneNumber(request.getPhoneNumber());
        return getCustomerResponse(customer);
    }

    @Override
    public AddressResponse addAddress(AddressRequest request) {
        Customer customer = getCustomerById(request.getCustomerId());
        return addressService.create(Address.builder()
                .address(request.getAddress())
                .customer(customer)
                .build());
    }

    @Override
    public AddressResponse updateAddress(UpdateAddressRequest request) {
        return addressService.update(request);
    }

    @Override
    public void deleteAddress(String addressId) {
        addressService.delete(addressId);
    }

    private CustomerResponse getCustomerResponse(Customer customer) {
        List<AddressResponse> addressResponses = customer.getAddresses().stream().map(
                address -> {
                    return AddressResponse.builder()
                            .id(address.getId())
                            .address(address.getAddress())
                            .build();
                }
        ).toList();
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .userAccountId(customer.getUserAccount().getId())
                .addresses(addressResponses)
                .build();
    }
}
