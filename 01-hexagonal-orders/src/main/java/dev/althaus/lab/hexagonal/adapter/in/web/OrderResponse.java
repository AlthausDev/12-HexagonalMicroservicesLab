package dev.althaus.lab.hexagonal.adapter.in.web;

import dev.althaus.lab.hexagonal.domain.model.Order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        String customerName,
        List<ItemResponse> items,
        BigDecimal total,
        Instant createdAt
) {

    static OrderResponse from(Order order) {
        List<ItemResponse> items = order.items().stream()
                .map(item -> new ItemResponse(
                        item.productCode(),
                        item.quantity(),
                        item.unitPrice(),
                        item.subtotal()
                ))
                .toList();

        return new OrderResponse(
                order.id(),
                order.customerName(),
                items,
                order.total(),
                order.createdAt()
        );
    }

    public record ItemResponse(
            String productCode,
            int quantity,
            BigDecimal unitPrice,
            BigDecimal subtotal
    ) {
    }
}
