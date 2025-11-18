package com.ecommerce.ecom.service.Impl;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecom.dto.CategoryRequest;
import com.ecommerce.ecom.dto.CategoryResponse;
import com.ecommerce.ecom.mapper.CategoryMapper;
import com.ecommerce.ecom.model.Category;
import com.ecommerce.ecom.repository.CategoryRepository;
import com.ecommerce.ecom.service.CategoryService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    // create
    @Override
    public CategoryResponse create(CategoryRequest request) {
        Category parent = null;
        String parentId = request.getParentId() != null ? request.getParentId() : null;
        if (parentId != null) {
            parent = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new EntityNotFoundException("Parent not found: " + parentId));
        }
        Category toSaveCategory = mapper.toEntity(request); // convert dto to entity
        toSaveCategory.setParent(parent); // attach parent permanenetly
        categoryRepository.findByParentIdAndSlug(parentId, toSaveCategory.getSlug()).ifPresent(exixting -> {
            throw new DataIntegrityViolationException("Slug already exists under this parent category");
        });

        Category savedCategory = categoryRepository.save(toSaveCategory);

        return mapper.toResponse(savedCategory);
    }

    // update
    @Override
    public CategoryResponse update(@NonNull String id, CategoryRequest request) {

        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found" + id));

        // parent update logic
        String parentId = request.getParentId();
        if (parentId != null) {
            Category parent = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new EntityNotFoundException("Parent not found: " + parentId));
            existing.setParent(parent);
        }

        // apply only non-null values
        mapper.updateEntityFromRequest(request, existing);

        // ensure slug is unique
        String parentIdForSlugCheck = existing.getParent() != null ? existing.getParent().getId() : null;

        categoryRepository.findByParentIdAndSlug(parentIdForSlugCheck, existing.getSlug()).ifPresent(conflict -> {
            if (!conflict.getId().equals(existing.getId())) {
                throw new DataIntegrityViolationException("Slug already exists under this parent category");
            }
        });

        Category saved = categoryRepository.save(existing);

        return mapper.toResponse(saved);
    }

    // soft delete
    @Override
    public void delete(@NonNull String id) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ca   tegory not found" + id));
        existing.setDeletedAt(OffsetDateTime.now());
        existing.setActive(false);
        categoryRepository.save(existing);
    }

    // get by id
    @Override
    public CategoryResponse getById(@NonNull String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found" + id));

        return mapper.toResponse(category);
    }

    // get all
    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream().map(mapper::toResponse).toList();
    }

    // get all active
    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllActive() {
        return categoryRepository.findByActiveTrue().stream().map(mapper::toResponse).toList();
    }

    // set active
    @Override
    public CategoryResponse setActive(@NonNull String id, boolean active) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found" + id));
        existing.setActive(active);
        return mapper.toResponse(categoryRepository.save(existing));
    }

    // update metadata
    @Override
    public CategoryResponse updateMetadata(@NonNull String id, String metadata) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found" + id));
        existing.setMetadata(metadata);
        return mapper.toResponse(categoryRepository.save(existing));
    }

    // get children
    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getChildren(@NonNull String parentId) {
        return categoryRepository.findByParentId(parentId).stream().map(mapper::toResponse).toList();
    }

    // set position
    @Override
    public CategoryResponse setPosition(@NonNull String id, int position) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found" + id));
        existing.setPosition(position);
        return mapper.toResponse(categoryRepository.save(existing));
    }

    // search
    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAll();
        }
        return categoryRepository
                .findByNameContainingIgnoreCaseOrSlugContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword,
                        keyword, keyword)
                .stream().map(mapper::toResponse).toList();
    }
}
