package com.tacos.data.jpa;

import com.tacos.domain.TacoOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository
        extends JpaRepository<TacoOrder, Long> {
}
