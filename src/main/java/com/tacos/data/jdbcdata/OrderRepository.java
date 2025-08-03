package com.tacos.data.jdbcdata;

import com.tacos.domain.TacoOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository
        extends CrudRepository<TacoOrder, Long> {
}
