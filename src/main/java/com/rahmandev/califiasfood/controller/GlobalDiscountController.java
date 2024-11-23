package com.rahmandev.califiasfood.controller;

import com.rahmandev.califiasfood.constant.APIUrl;
import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.GlobalDiscountRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateGlobalDiscountRequest;
import com.rahmandev.califiasfood.dto.response.CommonResponse;
import com.rahmandev.califiasfood.dto.response.GlobalDiscountResponse;
import com.rahmandev.califiasfood.service.GlobalDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.GLOBAL_DISCOUNT_API)
public class GlobalDiscountController {
    private final GlobalDiscountService globalDiscountService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<GlobalDiscountResponse>>> getAll() {
        List<GlobalDiscountResponse> globalDiscountResponses = globalDiscountService.getAll();
        CommonResponse<List<GlobalDiscountResponse>> response = CommonResponse.<List<GlobalDiscountResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(globalDiscountResponses)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<GlobalDiscountResponse>> getById(@PathVariable String id) {
        GlobalDiscountResponse globalDiscountResponse = globalDiscountService.getById(id);
        CommonResponse<GlobalDiscountResponse> response = CommonResponse.<GlobalDiscountResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(globalDiscountResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PostMapping
    public ResponseEntity<CommonResponse<GlobalDiscountResponse>> create(@RequestBody GlobalDiscountRequest request) {
        GlobalDiscountResponse globalDiscountResponse = globalDiscountService.create(request);
        CommonResponse<GlobalDiscountResponse> response = CommonResponse.<GlobalDiscountResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(globalDiscountResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PutMapping
    public ResponseEntity<CommonResponse<GlobalDiscountResponse>> update(@RequestBody UpdateGlobalDiscountRequest request) {
        GlobalDiscountResponse globalDiscountResponse = globalDiscountService.update(request);
        CommonResponse<GlobalDiscountResponse> response = CommonResponse.<GlobalDiscountResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(globalDiscountResponse)
                .build();
        return ResponseEntity.ok(response);
    }


    @PutMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<String>> updateStatusById(@PathVariable String id) {
        globalDiscountService.updateStatusGlobalDiscount(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<String>> delete(@PathVariable String id) {
        globalDiscountService.delete(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_DELETE_DATA)
                .build();
        return ResponseEntity.ok(response);
    }
}
