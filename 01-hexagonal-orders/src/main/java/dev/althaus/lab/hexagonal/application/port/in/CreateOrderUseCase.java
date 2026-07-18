package dev.althaus.lab.hexagonal.application.port.in;

import dev.althaus.lab.hexagonal.application.command.CreateOrderCommand;
import dev.althaus.lab.hexagonal.domain.model.Order;

public interface CreateOrderUseCase {

    Order create(CreateOrderCommand command);
}
