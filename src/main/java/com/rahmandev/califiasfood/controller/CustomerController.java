package com.rahmandev.califiasfood.controller;

import com.rahmandev.califiasfood.constant.APIUrl;
import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.search.SearchCustomerRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateCustomerRequest;
import com.rahmandev.califiasfood.dto.response.CommonResponse;
import com.rahmandev.califiasfood.dto.response.CustomerResponse;
import com.rahmandev.califiasfood.dto.response.PagingResponse;
import com.rahmandev.califiasfood.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIUrl.CUSTOMER_API)
public class CustomerController {
    private final CustomerService customerService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> getAll(
            @RequestParam(name = "q", required = false) String q,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        SearchCustomerRequest request = SearchCustomerRequest.builder()
                .q(q)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();
        Page<CustomerResponse> customers = customerService.findAll(request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPage(customers.getTotalPages())
                .totalElement(customers.getTotalElements())
                .page(customers.getPageable().getPageNumber() + 1)
                .size(customers.getPageable().getPageSize())
                .hasNext(customers.hasNext())
                .hasPrevious(customers.hasPrevious())
                .build();
        CommonResponse<List<CustomerResponse>> response = CommonResponse.<List<CustomerResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(customers.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<CustomerResponse>> getById(@PathVariable String id) {
        CustomerResponse customerResponse = customerService.findById(id);
        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(customerResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN') OR @authenticateUserServiceImpl.hasSameId(#request)")
    @PutMapping
    public ResponseEntity<CommonResponse<CustomerResponse>> update(@RequestBody UpdateCustomerRequest request) {
        CustomerResponse customerResponse = customerService.update(request);
        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(customerResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}
