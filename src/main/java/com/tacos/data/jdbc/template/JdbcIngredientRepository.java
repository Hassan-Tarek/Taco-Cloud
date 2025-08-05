package com.tacos.data.jdbc.template;

import com.tacos.data.jdbc.repository.IngredientRepository;
import com.tacos.domain.Ingredient;
import com.tacos.domain.IngredientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

//@Repository
public class JdbcIngredientRepository implements IngredientRepository {
    private final JdbcTemplate jdbcTemplate;

//    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query("SELECT id, name, type FROM ingredient", this::mapRowToIngredient);
    }

    public Optional<Ingredient> findById(String id) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                        "SELECT id, name, type FROM ingredient WHERE id=?",
                        this::mapRowToIngredient,
                        id
                )
        );
    }

    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update(
                "INSERT INTO ingredient (id, name, type) values (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getIngredientType().toString()
        );

        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet resultSet, int rowNum) throws SQLException {
        return new Ingredient(
                resultSet.getString("id"),
                resultSet.getString("name"),
                IngredientType.valueOf(resultSet.getString("type"))
        );
    }
}
