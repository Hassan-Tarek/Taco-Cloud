package com.tacos.data.jdbc;

import aj.org.objectweb.asm.Type;
import com.tacos.data.repository.IngredientRefRepository;
import com.tacos.data.repository.TacoRepository;
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

@Repository
public class JdbcTacoRepository implements TacoRepository {
    private final JdbcTemplate jdbcTemplate;
    private final IngredientRefRepository ingredientRefRepository;

    @Autowired
    public JdbcTacoRepository(JdbcTemplate jdbcTemplate,
                              IngredientRefRepository ingredientRefRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.ingredientRefRepository = ingredientRefRepository;
    }

    @Override
    public Taco save(Taco taco, long orderId, long orderKey) {
        PreparedStatementCreatorFactory pscf = getPreparedStatementCreatorFactory();
        pscf.setReturnGeneratedKeys(true);

        taco.setCreatedAt(new Date());
        PreparedStatementCreator psc = getPreparedStatementCreator(pscf, taco, orderId, orderKey);

        GeneratedKeyHolder gkh = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, gkh);
        long tacoId = Objects.requireNonNull(gkh.getKey()).longValue();
        taco.setId(tacoId);

        ingredientRefRepository.saveAll(taco.getIngredients(), tacoId);

        return taco;
    }

    @Override
    public void saveAll(List<Taco> tacos, long orderId) {
        int key = 0;
        for (Taco taco : tacos) {
            save(taco, orderId, key++);
        }
    }

    private static PreparedStatementCreatorFactory getPreparedStatementCreatorFactory() {
        return new PreparedStatementCreatorFactory(
                "INSERT INTO Taco "
                        + "(name, created_at, taco_order, taco_order_key) "
                        + "VALUES (?, ?, ?, ?)",
                Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG
        );
    }

    private static PreparedStatementCreator getPreparedStatementCreator(
            PreparedStatementCreatorFactory pscf, Taco taco, long orderId, long orderKey) {
        return pscf.newPreparedStatementCreator(
                Arrays.asList(
                        taco.getName(),
                        taco.getCreatedAt(),
                        orderId,
                        orderKey
                )
        );
    }}
