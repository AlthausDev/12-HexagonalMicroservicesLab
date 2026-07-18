package dev.althaus.lab.orders.application.service;

import dev.althaus.lab.orders.application.command.CreatePurchaseOrderCommand;
import dev.althaus.lab.orders.application.port.in.CreatePurchaseOrderUseCase;
import dev.althaus.lab.orders.application.port.in.QueryPurchaseOrderUseCase;
import dev.althaus.lab.orders.application.port.out.CatalogGateway;
import dev.althaus.lab.orders.application.port.out.PurchaseOrderRepository;
import dev.althaus.lab.orders.domain.ProductSnapshot;
import dev.althaus.lab.orders.domain.PurchaseOrder;

import java.util.List;
import java.util.Objects;

public final class CreatePurchaseOrderService
        implements CreatePurchaseOrderUseCase, QueryPurchaseOrderUseCase {

    private final CatalogGateway catalogGateway;
    private final PurchaseOrderRepository orderRepository;

    public CreatePurchaseOrderService(
            CatalogGateway catalogGateway,
            PurchaseOrderRepository orderRepository
    ) {
        this.catalogGateway = Objects.requireNonNull(catalogGateway);
        this.orderRepository = Objects.requireNonNull(orderRepository);
    }

    @Override
    public PurchaseOrder create(CreatePurchaseOrderCommand command) {
        Objects.requireNonNull(command, "El comando es obligatorio.");

        ProductSnapshot product = catalogGateway.findBySku(command.sku())
                .orElseThrow(() -> new IllegalArgumentException(
                        "El producto no existe en catalog-service."
                ));

        PurchaseOrder order = PurchaseOrder.create(
                product,
                command.quantity()
        );

        return orderRepository.save(order);
    }

    @Override
    public List<PurchaseOrder> findAll() {
        return orderRepository.findAll();
    }
}
