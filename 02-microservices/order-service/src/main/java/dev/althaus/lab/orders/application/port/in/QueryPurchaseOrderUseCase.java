package dev.althaus.lab.orders.application.port.in;

import dev.althaus.lab.orders.domain.PurchaseOrder;

import java.util.List;

public interface QueryPurchaseOrderUseCase {

    List<PurchaseOrder> findAll();
}
