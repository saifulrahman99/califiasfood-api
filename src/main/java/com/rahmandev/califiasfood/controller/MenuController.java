package com.rahmandev.califiasfood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahmandev.califiasfood.constant.APIUrl;
import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.MenuRequest;
import com.rahmandev.califiasfood.dto.request.search.SearchMenuRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateMenuRequest;
import com.rahmandev.califiasfood.dto.response.CommonResponse;
import com.rahmandev.califiasfood.dto.response.MenuResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rahmandev.califiasfood.dto.response.PagingResponse;
import com.rahmandev.califiasfood.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping
    public ResponseEntity<CommonResponse<List<MenuResponse>>> getAll(
            @RequestParam(name = "q", required = false) String q,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        SearchMenuRequest request = SearchMenuRequest.builder()
                .q(q)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();
        Page<MenuResponse> menus = menuService.getAll(request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPage(menus.getTotalPages())
                .totalElement(menus.getTotalElements())
                .page(menus.getPageable().getPageNumber() + 1)
                .size(menus.getPageable().getPageSize())
                .hasNext(menus.hasNext())
                .hasPrevious(menus.hasPrevious())
                .build();
        CommonResponse<List<MenuResponse>> response = CommonResponse.<List<MenuResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(menus.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<MenuResponse>> getById(@PathVariable("id") String id) {
        MenuResponse menuResponse = menuService.getById(id);
        CommonResponse<MenuResponse> response = CommonResponse.<MenuResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(menuResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<String>> delete(@PathVariable("id") String id) {
        menuService.delete(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_DELETE_DATA)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<MenuResponse>> update(
            @RequestPart(name = "menu") String jsonMenu,
            @RequestParam(name = "images", required = false) List<MultipartFile> images
    ) {
        CommonResponse.CommonResponseBuilder<MenuResponse> responseBuilder = CommonResponse.builder();
        try {
            UpdateMenuRequest request = objectMapper.readValue(jsonMenu, new TypeReference<>() {
            });
            request.setImages(images);
            MenuResponse menuResponse = menuService.update(request);
            CommonResponse<MenuResponse> response = responseBuilder
                    .statusCode(HttpStatus.OK.value())
                    .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                    .data(menuResponse)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            responseBuilder.message(ResponseMessage.ERROR_INTERNAL_SERVER);
            responseBuilder.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBuilder.build());
        }
    }
}
