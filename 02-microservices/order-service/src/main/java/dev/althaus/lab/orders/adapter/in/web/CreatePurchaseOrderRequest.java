package dev.althaus.lab.orders.adapter.in.web;

import dev.althaus.lab.orders.application.command.CreatePurchaseOrderCommand;

public record CreatePurchaseOrderRequest(
        String sku,
        int quantity
) {

    CreatePurchaseOrderCommand toCommand() {
        return new CreatePurchaseOrderCommand(sku, quantity);
    }
}
