package com.tacos.data.jpa;

import com.tacos.domain.TacoOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository
        extends JpaRepository<TacoOrder, Long> {
}
