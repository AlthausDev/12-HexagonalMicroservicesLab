package dev.althaus.lab.orders.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record PurchaseOrder(
        UUID id,
        String sku,
        String productName,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal total,
        Instant createdAt
) {

    public PurchaseOrder {
        Objects.requireNonNull(id, "El identificador es obligatorio.");
        Objects.requireNonNull(createdAt, "La fecha es obligatoria.");

        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("El SKU es obligatorio.");
        }
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        Objects.requireNonNull(unitPrice, "El precio unitario es obligatorio.");
        Objects.requireNonNull(total, "El total es obligatorio.");

        sku = sku.trim().toUpperCase();
        productName = productName.trim();
        unitPrice = unitPrice.setScale(2, RoundingMode.HALF_UP);
        total = total.setScale(2, RoundingMode.HALF_UP);
    }

    public static PurchaseOrder create(
            ProductSnapshot product,
            int quantity
    ) {
        Objects.requireNonNull(product, "El producto es obligatorio.");
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        BigDecimal total = product.price()
                .multiply(BigDecimal.valueOf(quantity));

        return new PurchaseOrder(
                UUID.randomUUID(),
                product.sku(),
                product.name(),
                quantity,
                product.price(),
                total,
                Instant.now()
        );
    }
}
