package com.tacos.data.jdbcdata;

import com.tacos.domain.Taco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface TacoRepository
        extends CrudRepository<Taco, Long> {
}
