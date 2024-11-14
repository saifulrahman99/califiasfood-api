package com.rahmandev.califiasfood.controller;

import com.rahmandev.califiasfood.constant.APIUrl;
import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.search.SearchDiscountRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateDiscountRequest;
import com.rahmandev.califiasfood.dto.response.CommonResponse;
import com.rahmandev.califiasfood.dto.response.DiscountResponse;
import com.rahmandev.califiasfood.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.DISCOUNT_API)
public class DiscountController {
    private final DiscountService discountService;

    @GetMapping
    public ResponseEntity<CommonResponse<Page<DiscountResponse>>> getAll(
            @RequestParam(name = "q", required = false) String q,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        SearchDiscountRequest request = SearchDiscountRequest.builder()
                .q(q)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();

        Page<DiscountResponse> discountResponses = discountService.getAll(request);
        CommonResponse<Page<DiscountResponse>> response = CommonResponse.<Page<DiscountResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(discountResponses)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<DiscountResponse>> getById(@PathVariable String id) {
        DiscountResponse discountResponse = discountService.getById(id);
        CommonResponse<DiscountResponse> response = CommonResponse.<DiscountResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(discountResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<DiscountResponse>> update(@RequestBody UpdateDiscountRequest request) {
        DiscountResponse discountResponse = discountService.update(request);
        CommonResponse<DiscountResponse> response = CommonResponse.<DiscountResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(discountResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<String>> update(@PathVariable String id) {
        discountService.updateStatusDiscount(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .build();
        return ResponseEntity.ok(response);
    }
}
