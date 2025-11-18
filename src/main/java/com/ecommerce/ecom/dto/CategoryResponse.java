package com.ecommerce.ecom.dto;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class CategoryResponse {

    private String id;
    private String name;
    private String slug;
    private String description;
    private boolean active;
    private Integer position;
    private String metadata;
    private String parentId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
