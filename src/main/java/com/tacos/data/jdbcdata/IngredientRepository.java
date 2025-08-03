package com.tacos.data.jdbcdata;

import com.tacos.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository
        extends CrudRepository<Ingredient, String> {
}
