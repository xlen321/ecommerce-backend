package com.ecommerce.ecom.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

import com.ecommerce.ecom.dto.CategoryRequest;
import com.ecommerce.ecom.dto.CategoryResponse;
import com.ecommerce.ecom.payload.ApiResponse;
import com.ecommerce.ecom.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

        private final CategoryService categoryService;

        // get all categories
        @GetMapping("/public/categories")
        public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories(
                        @RequestParam(value = "active", required = false) Boolean active) {
                List<CategoryResponse> categories = (active != null && active) ? categoryService.getAllActive()
                                : categoryService.getAll();
                return ResponseEntity.ok(ApiResponse.success("Categories fetched successfully", categories,
                                HttpStatus.OK.value()));
        }

        // search categories
        @GetMapping("/search")
        public ResponseEntity<ApiResponse<List<CategoryResponse>>> search(
                        @RequestParam("q") String keyword) {

                List<CategoryResponse> results = categoryService.search(keyword);

                return ResponseEntity.ok(
                                ApiResponse.success("Search results", results, HttpStatus.OK.value()));
        }

        // get children of a category
        @GetMapping("/{id}/children")
        public ResponseEntity<ApiResponse<List<CategoryResponse>>> getChildren(
                        @PathVariable @NonNull String id) {

                List<CategoryResponse> children = categoryService.getChildren(id);

                return ResponseEntity.ok(
                                ApiResponse.success("Fetched children", children, HttpStatus.OK.value()));
        }

        // create category
        @PostMapping("/admin/category")
        public ResponseEntity<ApiResponse<CategoryResponse>> create(
                        @RequestBody CategoryRequest request) {

                CategoryResponse created = categoryService.create(request);

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(ApiResponse.success("Category created", created, HttpStatus.CREATED.value()));
        }

        // update category
        @PutMapping("/admin/categories/{id}")
        public ResponseEntity<ApiResponse<CategoryResponse>> update(
                        @PathVariable @NonNull String id,
                        @RequestBody CategoryRequest request) {

                CategoryResponse updated = categoryService.update(id, request);

                return ResponseEntity.ok(
                                ApiResponse.success("Category updated", updated, HttpStatus.OK.value()));
        }

        // delete category
        @DeleteMapping("/admin/categories/{id}")
        public ResponseEntity<ApiResponse<Void>> delete(@PathVariable @NonNull String id) {

                categoryService.delete(id);

                return ResponseEntity.ok(
                                ApiResponse.success("Category deleted", null, HttpStatus.OK.value()));
        }

        // update metadata
        @PatchMapping("/admin/categories/{id}/metadata")
        public ResponseEntity<ApiResponse<CategoryResponse>> updateMetadata(
                        @PathVariable @NonNull String id,
                        @RequestBody Map<String, Object> metadata) {

                CategoryResponse updated = categoryService.updateMetadata(id, metadata);

                return ResponseEntity.ok(
                                ApiResponse.success("Metadata updated", updated, HttpStatus.OK.value()));
        }

        // update active
        @PatchMapping("/admin/categories/{id}/active")
        public ResponseEntity<ApiResponse<CategoryResponse>> setActive(
                        @PathVariable @NonNull String id,
                        @RequestParam("value") boolean active) {

                CategoryResponse updated = categoryService.setActive(id, active);

                return ResponseEntity.ok(
                                ApiResponse.success("Active status updated", updated, HttpStatus.OK.value()));
        }

        // update position
        @PatchMapping("/admin/categories/{id}/position")
        public ResponseEntity<ApiResponse<CategoryResponse>> updatePosition(
                        @PathVariable @NonNull String id,
                        @RequestParam("value") int position) {

                CategoryResponse updated = categoryService.setPosition(id, position);

                return ResponseEntity.ok(
                                ApiResponse.success("Category position updated", updated, HttpStatus.OK.value()));
        }
}
