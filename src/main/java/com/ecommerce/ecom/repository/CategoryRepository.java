package com.ecommerce.ecom.repository;

import com.ecommerce.ecom.model.Category;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findByActiveTrue();

    List<Category> findByParentId(String parentId);

    List<Category> findBySlugContainingIgnoreCase(String slug);

    List<Category> findByNameContainingIgnoreCase(String name);

    List<Category> findByNameContainingIgnoreCaseOrSlugContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String name, String slug, String description);

    Optional<Category> findByParentIdAndSlug(String parentId, String slug);

    Optional<Category> findByParentIdAndName(String parentId, String name);

}
