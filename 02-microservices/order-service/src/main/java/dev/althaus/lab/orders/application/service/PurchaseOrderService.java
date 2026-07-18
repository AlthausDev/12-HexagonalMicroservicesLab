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

public final class PurchaseOrderService
        implements CreatePurchaseOrderUseCase, QueryPurchaseOrderUseCase {

    private final CatalogGateway catalogGateway;
    private final PurchaseOrderRepository orderRepository;

    public PurchaseOrderService(
            CatalogGateway catalogGateway,
            PurchaseOrderRepository orderRepository
    ) {
        this.catalogGateway = Objects.requireNonNull(catalogGateway);
        this.orderRepository = Objects.requireNonNull(orderRepository);
    }

    @Override
    public PurchaseOrder create(CreatePurchaseOrderCommand command) {
        validate(command);

        String normalizedSku = command.sku().trim().toUpperCase();
        ProductSnapshot product = catalogGateway.findBySku(normalizedSku)
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

    private static void validate(CreatePurchaseOrderCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("El comando es obligatorio.");
        }
        if (command.sku() == null || command.sku().isBlank()) {
            throw new IllegalArgumentException("El SKU es obligatorio.");
        }
        if (command.quantity() <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor que cero."
            );
        }
    }
}
