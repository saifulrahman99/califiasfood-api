package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.CategoryRequest;
import com.rahmandev.califiasfood.entity.Category;
import com.rahmandev.califiasfood.repository.CategoryRepository;
import com.rahmandev.califiasfood.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Category create(CategoryRequest request) {
        try {
            Category category = Category.builder()
                    .name(request.getName())
                    .build();
            return categoryRepository.saveAndFlush(category);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ResponseMessage.DUPLICATE_KEY);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Category update(Category request) {
        if (categoryRepository.findByName(request.getName()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ResponseMessage.DUPLICATE_KEY);
        }
        Category category = getCategoryById(request.getId());
        category.setName(request.getName());
        return category;
    }

    @Override
    public Category getCategoryById(String id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
