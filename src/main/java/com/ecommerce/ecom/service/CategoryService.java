package com.ecommerce.ecom.service;

import java.util.List;
import java.util.Map;

import org.springframework.lang.NonNull;

import com.ecommerce.ecom.dto.CategoryRequest;
import com.ecommerce.ecom.dto.CategoryResponse;

public interface CategoryService {

    CategoryResponse create(CategoryRequest category);

    CategoryResponse update(@NonNull String id, CategoryRequest category);

    void delete(@NonNull String id);

    CategoryResponse getById(@NonNull String id);

    List<CategoryResponse> getAllActive();

    List<CategoryResponse> getAll();

    CategoryResponse setActive(@NonNull String id, boolean active);

    CategoryResponse updateMetadata(@NonNull String id, Map<String, Object> metadata);

    List<CategoryResponse> getChildren(@NonNull String parentId);

    CategoryResponse setPosition(@NonNull String id, int position);

    List<CategoryResponse> search(String keyword);

}
