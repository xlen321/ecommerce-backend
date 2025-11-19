package com.ecommerce.ecom.dto;

import java.time.OffsetDateTime;
import java.util.Map;

import lombok.Data;

@Data
public class CategoryResponse {

    private String id;
    private String name;
    private String slug;
    private String description;
    private boolean active;
    private Integer position;
    private Map<String, Object> metadata;
    private String parentId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
