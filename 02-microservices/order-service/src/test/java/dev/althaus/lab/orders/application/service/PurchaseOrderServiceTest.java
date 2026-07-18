package dev.althaus.lab.orders.application.service;

import dev.althaus.lab.orders.application.command.CreatePurchaseOrderCommand;
import dev.althaus.lab.orders.application.port.out.CatalogGateway;
import dev.althaus.lab.orders.application.port.out.PurchaseOrderRepository;
import dev.althaus.lab.orders.domain.ProductSnapshot;
import dev.althaus.lab.orders.domain.PurchaseOrder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PurchaseOrderServiceTest {

    @Test
    void createsAnOrderUsingAFakeRemoteCatalog() {
        CatalogGateway catalogGateway = sku -> Optional.of(
                new ProductSnapshot(
                        "HEX-BOOK",
                        "Hexagonal Architecture",
                        new BigDecimal("29.90")
                )
        );
        FakePurchaseOrderRepository repository =
                new FakePurchaseOrderRepository();
        PurchaseOrderService service = new PurchaseOrderService(
                catalogGateway,
                repository
        );

        PurchaseOrder order = service.create(
                new CreatePurchaseOrderCommand("HEX-BOOK", 2)
        );

        assertThat(order.productName())
                .isEqualTo("Hexagonal Architecture");
        assertThat(order.total())
                .isEqualByComparingTo("59.80");
        assertThat(repository.findAll())
                .containsExactly(order);
    }

    @Test
    void rejectsInvalidQuantityBeforeCallingCatalog() {
        AtomicInteger catalogCalls = new AtomicInteger();
        CatalogGateway catalogGateway = sku -> {
            catalogCalls.incrementAndGet();
            return Optional.empty();
        };
        PurchaseOrderService service = new PurchaseOrderService(
                catalogGateway,
                new FakePurchaseOrderRepository()
        );

        assertThatThrownBy(() -> service.create(
                new CreatePurchaseOrderCommand("HEX-BOOK", 0)
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La cantidad debe ser mayor que cero.");

        assertThat(catalogCalls).hasValue(0);
    }

    @Test
    void rejectsBlankSkuBeforeCallingCatalog() {
        AtomicInteger catalogCalls = new AtomicInteger();
        CatalogGateway catalogGateway = sku -> {
            catalogCalls.incrementAndGet();
            return Optional.empty();
        };
        PurchaseOrderService service = new PurchaseOrderService(
                catalogGateway,
                new FakePurchaseOrderRepository()
        );

        assertThatThrownBy(() -> service.create(
                new CreatePurchaseOrderCommand("  ", 1)
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El SKU es obligatorio.");

        assertThat(catalogCalls).hasValue(0);
    }

    @Test
    void rejectsUnknownProduct() {
        CatalogGateway catalogGateway = sku -> Optional.empty();
        PurchaseOrderService service = new PurchaseOrderService(
                catalogGateway,
                new FakePurchaseOrderRepository()
        );

        assertThatThrownBy(() -> service.create(
                new CreatePurchaseOrderCommand("UNKNOWN", 1)
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El producto no existe en catalog-service.");
    }

    private static final class FakePurchaseOrderRepository
            implements PurchaseOrderRepository {

        private final List<PurchaseOrder> orders = new ArrayList<>();

        @Override
        public PurchaseOrder save(PurchaseOrder order) {
            orders.add(order);
            return order;
        }

        @Override
        public List<PurchaseOrder> findAll() {
            return List.copyOf(orders);
        }
    }
}
