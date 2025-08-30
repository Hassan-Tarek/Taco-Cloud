package com.tacos.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RestResource(rel = "ingredients", path = "ingredients")
public class Ingredient {
    @Id
    @Column(name = "id")
    private final String id;

    @Column(name = "name")
    private final String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private final IngredientType ingredientType;
}
