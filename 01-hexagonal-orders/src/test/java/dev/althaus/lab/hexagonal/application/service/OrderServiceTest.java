package dev.althaus.lab.hexagonal.application.service;

import dev.althaus.lab.hexagonal.application.command.CreateOrderCommand;
import dev.althaus.lab.hexagonal.application.port.out.OrderRepository;
import dev.althaus.lab.hexagonal.domain.model.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceTest {

    @Test
    void createsAnOrderWithoutSpringOrDatabase() {
        OrderRepository repository = new FakeOrderRepository();
        OrderService service = new OrderService(repository);

        Order order = service.create(new CreateOrderCommand(
                "Sam",
                List.of(new CreateOrderCommand.Item(
                        "hex-book",
                        2,
                        new BigDecimal("19.95")
                ))
        ));

        assertThat(order.customerName()).isEqualTo("Sam");
        assertThat(order.items()).hasSize(1);
        assertThat(order.total()).isEqualByComparingTo("39.90");
        assertThat(service.findById(order.id())).contains(order);
    }

    private static final class FakeOrderRepository implements OrderRepository {

        private final List<Order> orders = new ArrayList<>();

        @Override
        public Order save(Order order) {
            orders.add(order);
            return order;
        }

        @Override
        public Optional<Order> findById(UUID id) {
            return orders.stream()
                    .filter(order -> order.id().equals(id))
                    .findFirst();
        }

        @Override
        public List<Order> findAll() {
            return List.copyOf(orders);
        }
    }
}
