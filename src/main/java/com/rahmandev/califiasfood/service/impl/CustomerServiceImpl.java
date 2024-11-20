package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.search.SearchCustomerRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateCustomerRequest;
import com.rahmandev.califiasfood.dto.response.AddressResponse;
import com.rahmandev.califiasfood.dto.response.CustomerResponse;
import com.rahmandev.califiasfood.entity.Customer;
import com.rahmandev.califiasfood.repository.CustomerRepository;
import com.rahmandev.califiasfood.service.CustomerService;
import com.rahmandev.califiasfood.specification.CustomerSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer create(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public Page<CustomerResponse> findAll(SearchCustomerRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);

        Specification<Customer> specification = CustomerSpecification.getSpecification(request.getQ());
        Page<Customer> customers = customerRepository.findAll(specification, pageable);

        List<CustomerResponse> customerResponse = customers.getContent().stream().map(
                customer -> {
                    return CustomerResponse.builder()
                            .id(customer.getId())
                            .name(customer.getName())
                            .phoneNumber(customer.getPhoneNumber())
                            .userAccountId(customer.getUserAccount().getId())
                            .addresses(
                                    customer.getAddresses().stream().map(
                                            address -> {
                                                return AddressResponse.builder()
                                                        .id(address.getId())
                                                        .address(address.getAddress())
                                                        .customerId(customer.getId())
                                                        .build();
                                            }
                                    ).toList()
                            ).build();
                }
        ).toList();

        return new PageImpl<>(customerResponse, pageable, customers.getTotalElements());
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
