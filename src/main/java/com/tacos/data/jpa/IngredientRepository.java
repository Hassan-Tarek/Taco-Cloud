package com.tacos.data.jpa;

import com.tacos.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository
        extends JpaRepository<Ingredient, String> {
}
