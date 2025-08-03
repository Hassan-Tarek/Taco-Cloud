package com.tacos.data.repository;

import com.tacos.domain.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
