package dev.althaus.lab.hexagonal.adapter.in.web;

import dev.althaus.lab.hexagonal.application.command.CreateOrderCommand;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderRequest(
        String customerName,
        List<ItemRequest> items
) {

    CreateOrderCommand toCommand() {
        List<CreateOrderCommand.Item> commandItems = items == null
                ? List.of()
                : items.stream()
                        .map(item -> new CreateOrderCommand.Item(
                                item.productCode(),
                                item.quantity(),
                                item.unitPrice()
                        ))
                        .toList();

        return new CreateOrderCommand(customerName, commandItems);
    }

    public record ItemRequest(
            String productCode,
            int quantity,
            BigDecimal unitPrice
    ) {
    }
}
