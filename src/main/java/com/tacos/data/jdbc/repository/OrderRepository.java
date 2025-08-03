package com.tacos.data.jdbc.repository;

import com.tacos.domain.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
