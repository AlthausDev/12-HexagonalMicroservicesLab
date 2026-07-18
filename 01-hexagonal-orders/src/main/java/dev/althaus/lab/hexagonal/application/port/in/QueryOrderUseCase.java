package dev.althaus.lab.hexagonal.application.port.in;

import dev.althaus.lab.hexagonal.domain.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QueryOrderUseCase {

    Optional<Order> findById(UUID id);

    List<Order> findAll();
}
