package dev.althaus.lab.orders.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record ProductSnapshot(
        String sku,
        String name,
        BigDecimal price
) {

    public ProductSnapshot {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("El SKU es obligatorio.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio.");
        }

        Objects.requireNonNull(price, "El precio es obligatorio.");
        if (price.signum() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }

        sku = sku.trim().toUpperCase();
        name = name.trim();
        price = price.setScale(2, RoundingMode.HALF_UP);
    }
}
