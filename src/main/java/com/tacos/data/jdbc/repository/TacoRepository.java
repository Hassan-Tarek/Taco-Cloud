package com.tacos.data.jdbc.repository;

import com.tacos.domain.Taco;

import java.util.List;

public interface TacoRepository {
    Taco save(Taco taco, long orderId, long orderKey);
    void saveAll(List<Taco> tacos, long orderId);
}
