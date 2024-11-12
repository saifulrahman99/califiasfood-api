package com.rahmandev.califiasfood.service;

import com.rahmandev.califiasfood.dto.request.CategoryRequest;
import com.rahmandev.califiasfood.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    Category create(CategoryRequest request);

    Category update(Category request);

    Category getCategoryById(String id);

    void delete(String id);

    List<Category> getAll();

}
