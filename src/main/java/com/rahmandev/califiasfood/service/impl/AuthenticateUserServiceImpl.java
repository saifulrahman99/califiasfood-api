package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.update.UpdateCustomerRequest;
import com.rahmandev.califiasfood.entity.Customer;
import com.rahmandev.califiasfood.entity.UserAccount;
import com.rahmandev.califiasfood.service.CustomerService;
import com.rahmandev.califiasfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticateUserServiceImpl {
    private final CustomerService customerService;
    private final UserService userService;

    public boolean hasSameId(UpdateCustomerRequest request) {
        Customer currentCustomer = customerService.getCustomerById(request.getId());
        UserAccount userAccount = userService.getByContext();

        if (!userAccount.getId().equals(currentCustomer.getUserAccount().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ResponseMessage.ERROR_FORBIDDEN);
        }
        return true;
    }
}
