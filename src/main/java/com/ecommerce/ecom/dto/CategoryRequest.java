package com.ecommerce.ecom.dto;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequest {

    @NotBlank
    @Size(max = 255)
    private String name;

    // optional - will be auto-generated from name when absent
    @Size(max = 255)
    private String slug;

    // optional descriptive text
    private String description;

    // optional - default true
    private Boolean active;

    // optional - default 0
    private Integer position;

    // optional JSON stored as string
    private Map<String, Object> metadata;

    // optional parent id
    private String parentId;
}
