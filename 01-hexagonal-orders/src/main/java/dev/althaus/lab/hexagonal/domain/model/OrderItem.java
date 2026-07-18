package dev.althaus.lab.hexagonal.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record OrderItem(
        String productCode,
        int quantity,
        BigDecimal unitPrice
) {

    public OrderItem {
        if (productCode == null || productCode.isBlank()) {
            throw new IllegalArgumentException("El código de producto es obligatorio.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        Objects.requireNonNull(unitPrice, "El precio es obligatorio.");
        if (unitPrice.signum() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }

        productCode = productCode.trim().toUpperCase();
        unitPrice = unitPrice.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal subtotal() {
        return unitPrice
                .multiply(BigDecimal.valueOf(quantity))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
