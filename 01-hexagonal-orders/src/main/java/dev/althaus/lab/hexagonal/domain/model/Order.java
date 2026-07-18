package dev.althaus.lab.hexagonal.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class Order {

    private final UUID id;
    private final String customerName;
    private final List<OrderItem> items;
    private final BigDecimal total;
    private final Instant createdAt;

    private Order(
            UUID id,
            String customerName,
            List<OrderItem> items,
            BigDecimal total,
            Instant createdAt
    ) {
        this.id = Objects.requireNonNull(id);
        this.customerName = Objects.requireNonNull(customerName);
        this.items = List.copyOf(items);
        this.total = Objects.requireNonNull(total);
        this.createdAt = Objects.requireNonNull(createdAt);
    }

    public static Order create(String customerName, List<OrderItem> items) {
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio.");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("El pedido debe contener al menos una línea.");
        }

        List<OrderItem> safeItems = List.copyOf(items);
        BigDecimal total = safeItems.stream()
                .map(OrderItem::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new Order(
                UUID.randomUUID(),
                customerName.trim(),
                safeItems,
                total,
                Instant.now()
        );
    }

    public UUID id() {
        return id;
    }

    public String customerName() {
        return customerName;
    }

    public List<OrderItem> items() {
        return items;
    }

    public BigDecimal total() {
        return total;
    }

    public Instant createdAt() {
        return createdAt;
    }
}
