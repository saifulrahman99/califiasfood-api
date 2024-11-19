package com.rahmandev.califiasfood.service;

import com.rahmandev.califiasfood.dto.request.AddressRequest;
import com.rahmandev.califiasfood.dto.request.CustomerRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateAddressRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateCustomerRequest;
import com.rahmandev.califiasfood.dto.response.AddressResponse;
import com.rahmandev.califiasfood.dto.response.CustomerResponse;
import com.rahmandev.califiasfood.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    Customer create(Customer customer);
    Page<CustomerResponse> findAll(CustomerRequest request);

    CustomerResponse findById(String id);

    Customer getCustomerById(String id);

    CustomerResponse findByUserAccountId(String id);

    CustomerResponse update(UpdateCustomerRequest request);

    AddressResponse addAddress(AddressRequest request);

    AddressResponse updateAddress(UpdateAddressRequest request);

    void deleteAddress(String addressId);
}
