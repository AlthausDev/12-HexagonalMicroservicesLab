package dev.althaus.lab.hexagonal.application.command;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderCommand(
        String customerName,
        List<Item> items
) {

    public record Item(
            String productCode,
            int quantity,
            BigDecimal unitPrice
    ) {
    }
}
