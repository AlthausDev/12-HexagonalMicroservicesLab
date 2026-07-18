package dev.althaus.lab.hexagonal.config;

import dev.althaus.lab.hexagonal.application.port.out.OrderRepository;
import dev.althaus.lab.hexagonal.application.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfiguration {

    @Bean
    OrderService orderService(OrderRepository orderRepository) {
        return new OrderService(orderRepository);
    }
}
