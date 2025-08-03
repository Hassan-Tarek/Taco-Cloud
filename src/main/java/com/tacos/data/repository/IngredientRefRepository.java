package com.tacos.data.repository;

import com.tacos.domain.IngredientRef;

import java.util.List;

public interface IngredientRefRepository {
    IngredientRef save(IngredientRef ingredientRef, long tacoId, long tacoKey);
    void saveAll(List<IngredientRef> ingredientRefs, long tacoId);
}
