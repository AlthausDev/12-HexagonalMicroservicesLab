package dev.althaus.lab.orders.application.port.out;

import dev.althaus.lab.orders.domain.ProductSnapshot;

import java.util.Optional;

public interface CatalogGateway {

    Optional<ProductSnapshot> findBySku(String sku);
}
