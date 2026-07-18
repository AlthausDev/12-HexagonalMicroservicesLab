package dev.althaus.lab.orders.config;

import dev.althaus.lab.orders.application.port.out.CatalogGateway;
import dev.althaus.lab.orders.application.port.out.PurchaseOrderRepository;
import dev.althaus.lab.orders.application.service.CreatePurchaseOrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PurchaseOrderConfiguration {

    @Bean
    CreatePurchaseOrderService createPurchaseOrderService(
            CatalogGateway catalogGateway,
            PurchaseOrderRepository orderRepository
    ) {
        return new CreatePurchaseOrderService(
                catalogGateway,
                orderRepository
        );
    }
}
