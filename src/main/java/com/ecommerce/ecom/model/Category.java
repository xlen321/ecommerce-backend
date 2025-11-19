package com.ecommerce.ecom.model;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories", indexes = {
                @Index(name = "idx_categories_parent", columnList = "parent_id"),
                @Index(name = "idx_categories_active", columnList = "active")
}, uniqueConstraints = {
                @UniqueConstraint(name = "ux_categories_parent_slug", columnNames = { "parent_id", "slug" })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Category {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private String id;

        @Column(nullable = false)
        private String name;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_categories_parent"))
        private Category parent;

        @Builder.Default
        @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<Category> children = new HashSet<>();

        @Column(nullable = false)
        private String slug;

        @Column(nullable = false, columnDefinition = "text")
        private String description;

        @Builder.Default
        @Column(nullable = false)
        private boolean active = true;

        @Builder.Default
        @Column(nullable = false)
        private Integer position = 0;

        // JSONB metadata field
        @Builder.Default
        @JdbcTypeCode(SqlTypes.JSON)
        @Column(columnDefinition = "jsonb", nullable = false)
        private Map<String, Object> metadata = new HashMap<>();

        // Audit fields
        @CreationTimestamp
        @Column(name = "created_at", nullable = false, updatable = false)
        private OffsetDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at", nullable = false)
        private OffsetDateTime updatedAt;

        @Column(name = "deleted_at")
        private OffsetDateTime deletedAt;
}