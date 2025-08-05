package com.tacos.data.jdbc.template;

import aj.org.objectweb.asm.Type;
import com.tacos.data.jdbc.repository.TacoRepository;
import com.tacos.domain.Ingredient;
import com.tacos.domain.Taco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

//@Repository
public class JdbcTacoRepository implements TacoRepository {
    private final JdbcTemplate jdbcTemplate;

//    @Autowired
    public JdbcTacoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Taco save(Taco taco, long orderId, long orderKey) {
        PreparedStatementCreatorFactory pscf = getPreparedStatementCreatorFactory();
        pscf.setReturnGeneratedKeys(true);

        taco.setCreatedAt(new Date());
        PreparedStatementCreator psc = getPreparedStatementCreator(pscf, taco, orderId);

        GeneratedKeyHolder gkh = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, gkh);
        long tacoId = Objects.requireNonNull(gkh.getKey()).longValue();
        taco.setId(tacoId);

        saveIngredientsToTaco(tacoId, taco.getIngredients());

        return taco;
    }

    public void saveAll(List<Taco> tacos, long orderId) {
        int key = 0;
        for (Taco taco : tacos) {
            save(taco, orderId, key++);
        }
    }

    private void saveIngredientsToTaco(long tacoId, List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            jdbcTemplate.update(
                    "INSERT INTO taco_ingredients "
                    + "(taco_id, ingredient_id) "
                    + "VALUES (?, ?)",
                    tacoId, ingredient.getId()
            );
        }
    }

    private static PreparedStatementCreatorFactory getPreparedStatementCreatorFactory() {
        return new PreparedStatementCreatorFactory(
                "INSERT INTO Taco "
                        + "(name, taco_order_id, created_at) "
                        + "VALUES (?, ?, ?)",
                Types.VARCHAR, Type.LONG, Types.TIMESTAMP
        );
    }

    private static PreparedStatementCreator getPreparedStatementCreator(
            PreparedStatementCreatorFactory pscf, Taco taco, long orderId) {
        return pscf.newPreparedStatementCreator(
                Arrays.asList(
                        taco.getName(),
                        orderId,
                        taco.getCreatedAt()
                )
        );
    }}
