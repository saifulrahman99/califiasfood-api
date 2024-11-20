package com.rahmandev.califiasfood.controller;

import com.rahmandev.califiasfood.constant.APIUrl;
import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.AddressRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateAddressRequest;
import com.rahmandev.califiasfood.dto.response.AddressResponse;
import com.rahmandev.califiasfood.dto.response.CommonResponse;
import com.rahmandev.califiasfood.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.ADDRESS_API)
public class AddressController {
    private static final Logger log = LoggerFactory.getLogger(AddressController.class);
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<CommonResponse<AddressResponse>> create(@RequestBody AddressRequest request) {
        AddressResponse addressResponse = addressService.create(request);
        CommonResponse<AddressResponse> response = CommonResponse.<AddressResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(addressResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<AddressResponse>> update(@RequestBody UpdateAddressRequest request) {
        AddressResponse addressResponse = addressService.update(request);
        CommonResponse<AddressResponse> response = CommonResponse.<AddressResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(addressResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<String>> delete(@PathVariable String id) {
        addressService.delete(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_DELETE_DATA)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<AddressResponse>> getById(@PathVariable String id) {
        AddressResponse addressResponse = addressService.findById(id);
        CommonResponse<AddressResponse> response = CommonResponse.<AddressResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(addressResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/customer/{id}")
    public ResponseEntity<CommonResponse<List<AddressResponse>>> getAll(@PathVariable("id") String customerId) {
        List<AddressResponse> addressResponses = addressService.getAllByCustomerId(customerId);
        CommonResponse<List<AddressResponse>> response = CommonResponse.<List<AddressResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(addressResponses)
                .build();
        return ResponseEntity.ok(response);
    }
}
