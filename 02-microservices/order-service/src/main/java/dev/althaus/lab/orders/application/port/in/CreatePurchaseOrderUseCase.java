package dev.althaus.lab.orders.application.port.in;

import dev.althaus.lab.orders.application.command.CreatePurchaseOrderCommand;
import dev.althaus.lab.orders.domain.PurchaseOrder;

public interface CreatePurchaseOrderUseCase {

    PurchaseOrder create(CreatePurchaseOrderCommand command);
}
