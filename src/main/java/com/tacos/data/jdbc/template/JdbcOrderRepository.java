package com.tacos.data.jdbc.template;

import com.tacos.data.jdbc.repository.OrderRepository;
import com.tacos.data.jdbc.repository.TacoRepository;
import com.tacos.domain.TacoOrder;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

//@Repository
public class JdbcOrderRepository implements OrderRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TacoRepository tacoRepository;

//    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbcTemplate,
                               TacoRepository tacoRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.tacoRepository = tacoRepository;
    }

    @Transactional
    public TacoOrder save(TacoOrder order) {
        PreparedStatementCreatorFactory pscf = getPreparedStatementCreatorFactory();
        pscf.setReturnGeneratedKeys(true);

        order.setPlacedAt(new Date());
        PreparedStatementCreator psc = getPreparedStatementCreator(pscf, order);

        GeneratedKeyHolder gkh = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, gkh);
        long orderId = Objects.requireNonNull(gkh.getKey()).longValue();
        order.setId(orderId);

        tacoRepository.saveAll(order.getTacos(), orderId);

        return order;
    }

    private static PreparedStatementCreatorFactory getPreparedStatementCreatorFactory() {
        return new PreparedStatementCreatorFactory(
                "INSERT INTO Taco_Order "
                + "(delivery_name, delivery_street, delivery_city, "
                + "delivery_state, delivery_zip, cc_number, "
                + "cc_expiration, cc_cvv, placed_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
        );
    }

    private static PreparedStatementCreator getPreparedStatementCreator(
            PreparedStatementCreatorFactory pscf, TacoOrder order) {
        return pscf.newPreparedStatementCreator(
                Arrays.asList(
                        order.getDeliveryName(),
                        order.getDeliveryStreet(),
                        order.getDeliveryCity(),
                        order.getDeliveryState(),
                        order.getDeliveryZip(),
                        order.getCcNumber(),
                        order.getCcExpiration(),
                        order.getCcCVV(),
                        order.getPlacedAt()
                )
        );
    }
}
