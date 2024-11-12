package com.rahmandev.califiasfood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahmandev.califiasfood.constant.APIUrl;
import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.MenuRequest;
import com.rahmandev.califiasfood.dto.response.CommonResponse;
import com.rahmandev.califiasfood.dto.response.MenuResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rahmandev.califiasfood.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.MENU_API)
public class MenuController {
    private final MenuService menuService;
    private final ObjectMapper objectMapper;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<MenuResponse>> create(
            @RequestPart(name = "menu") String jsonMenu,
            @RequestParam(name = "images", required = true) List<MultipartFile> images
    ) {
        CommonResponse.CommonResponseBuilder<MenuResponse> responseBuilder = CommonResponse.builder();
        try {
            MenuRequest request = objectMapper.readValue(jsonMenu, new TypeReference<>() {
            });
            request.setImages(images);
            MenuResponse menuResponse = menuService.create(request);
            CommonResponse<MenuResponse> response = responseBuilder
                    .statusCode(HttpStatus.CREATED.value())
                    .message(ResponseMessage.SUCCESS_SAVE_DATA)
                    .data(menuResponse)
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            responseBuilder.message(ResponseMessage.ERROR_INTERNAL_SERVER);
            responseBuilder.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBuilder.build());
        }
    }

}
