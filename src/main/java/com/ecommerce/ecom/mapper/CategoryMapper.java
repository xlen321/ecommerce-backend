package com.ecommerce.ecom.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.ecommerce.ecom.dto.CategoryRequest;
import com.ecommerce.ecom.dto.CategoryResponse;
import com.ecommerce.ecom.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    // DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Category toEntity(CategoryRequest request);

    // Entity -> DTO
    @Mapping(target = "parentId", source = "parent.id")
    CategoryResponse toResponse(Category entity);

    // Update Entity From Request (PATCH / PUT)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "children", ignore = true)
    Category updateEntityFromRequest(CategoryRequest request, @MappingTarget Category entity);
}
