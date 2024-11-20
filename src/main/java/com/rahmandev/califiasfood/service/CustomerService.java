package com.rahmandev.califiasfood.service;

import com.rahmandev.califiasfood.dto.request.search.SearchCustomerRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateCustomerRequest;
import com.rahmandev.califiasfood.dto.response.CustomerResponse;
import com.rahmandev.califiasfood.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    Customer create(Customer customer);

    Page<CustomerResponse> findAll(SearchCustomerRequest request);

    CustomerResponse findById(String id);

    Customer getCustomerById(String id);

    CustomerResponse findByUserAccountId(String id);

    CustomerResponse update(UpdateCustomerRequest request);
}