package com.tacos.data.jdbc.template;

import com.tacos.data.jdbc.repository.IngredientRefRepository;
import com.tacos.domain.IngredientRef;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcIngredientRefRepository implements IngredientRefRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcIngredientRefRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public IngredientRef save(IngredientRef ingredientRef, long tacoId, long tacoKey) {
        jdbcTemplate.update(
                "INSERT INTO Ingredient_Ref "
                        + "(ingredient, taco, taco_key)"
                        + "VALUES (?, ?, ?)",
                ingredientRef.getIngredient(), tacoId, tacoKey
        );

        return ingredientRef;
    }

    public void saveAll(List<IngredientRef> ingredientRefs, long tacoId) {
        int key = 0;
        for (IngredientRef ingredientRef : ingredientRefs) {
            save(ingredientRef, tacoId, key++);
        }
    }
}
