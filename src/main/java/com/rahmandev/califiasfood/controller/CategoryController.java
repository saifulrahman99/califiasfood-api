package com.rahmandev.califiasfood.controller;

import com.rahmandev.califiasfood.constant.APIUrl;
import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.CategoryRequest;
import com.rahmandev.califiasfood.dto.response.CommonResponse;
import com.rahmandev.califiasfood.entity.Category;
import com.rahmandev.califiasfood.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.CATEGORY_API)
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<Category>>> getAll() {
        List<Category> categories = categoryService.getAll();
        CommonResponse<List<Category>> response = CommonResponse.<List<Category>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(categories)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PostMapping
    public ResponseEntity<CommonResponse<Category>> create(@RequestBody CategoryRequest request) {
        Category category = categoryService.create(request);
        CommonResponse<Category> response = CommonResponse.<Category>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(category)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PutMapping
    public ResponseEntity<CommonResponse<Category>> update(@RequestBody Category request) {
        Category category = categoryService.update(request);
        CommonResponse<Category> response = CommonResponse.<Category>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(category)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<String>> delete(@PathVariable("id") String id) {
        categoryService.delete(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_DELETE_DATA)
                .build();
        return ResponseEntity.ok(response);
    }
}
