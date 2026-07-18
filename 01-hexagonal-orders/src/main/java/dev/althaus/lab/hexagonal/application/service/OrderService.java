package dev.althaus.lab.hexagonal.application.service;

import dev.althaus.lab.hexagonal.application.command.CreateOrderCommand;
import dev.althaus.lab.hexagonal.application.port.in.CreateOrderUseCase;
import dev.althaus.lab.hexagonal.application.port.in.QueryOrderUseCase;
import dev.althaus.lab.hexagonal.application.port.out.OrderRepository;
import dev.althaus.lab.hexagonal.domain.model.Order;
import dev.althaus.lab.hexagonal.domain.model.OrderItem;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class OrderService implements CreateOrderUseCase, QueryOrderUseCase {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = Objects.requireNonNull(orderRepository);
    }

    @Override
    public Order create(CreateOrderCommand command) {
        Objects.requireNonNull(command, "El comando es obligatorio.");

        List<OrderItem> items = command.items() == null
                ? List.of()
                : command.items().stream()
                        .map(item -> new OrderItem(
                                item.productCode(),
                                item.quantity(),
                                item.unitPrice()
                        ))
                        .toList();

        Order order = Order.create(command.customerName(), items);
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return orderRepository.findById(Objects.requireNonNull(id));
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
