package dev.althaus.lab.orders.application.port.out;

import dev.althaus.lab.orders.domain.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderRepository {

    PurchaseOrder save(PurchaseOrder order);

    List<PurchaseOrder> findAll();
}
