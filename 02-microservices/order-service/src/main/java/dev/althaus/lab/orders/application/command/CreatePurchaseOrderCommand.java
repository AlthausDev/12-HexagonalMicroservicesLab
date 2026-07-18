package dev.althaus.lab.orders.application.command;

public record CreatePurchaseOrderCommand(
        String sku,
        int quantity
) {
}
