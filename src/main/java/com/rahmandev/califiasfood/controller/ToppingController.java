package com.rahmandev.califiasfood.controller;

import com.rahmandev.califiasfood.constant.APIUrl;
import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.ToppingRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateToppingRequest;
import com.rahmandev.califiasfood.dto.response.CommonResponse;
import com.rahmandev.califiasfood.dto.response.ToppingResponse;
import com.rahmandev.califiasfood.service.ToppingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.TOPPING_API)
public class ToppingController {
    private final ToppingService toppingService;

    @PostMapping
    public ResponseEntity<CommonResponse<ToppingResponse>> create(@RequestBody ToppingRequest request) {
        ToppingResponse toppingResponse = toppingService.create(request);
        CommonResponse<ToppingResponse> response = CommonResponse.<ToppingResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(toppingResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<ToppingResponse>> update(@RequestBody UpdateToppingRequest request) {
        ToppingResponse toppingResponse = toppingService.update(request);
        CommonResponse<ToppingResponse> response = CommonResponse.<ToppingResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(toppingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<String>> delete(@PathVariable String id) {
        toppingService.delete(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_DELETE_DATA)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ToppingResponse>>> getAll() {
        List<ToppingResponse> toppingResponses = toppingService.getAll();
        CommonResponse<List<ToppingResponse>> response = CommonResponse.<List<ToppingResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(toppingResponses)
                .build();
        return ResponseEntity.ok(response);
    }
}
