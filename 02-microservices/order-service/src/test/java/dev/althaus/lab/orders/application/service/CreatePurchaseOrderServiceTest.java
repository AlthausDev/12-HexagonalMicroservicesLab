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

import static org.assertj.core.api.Assertions.assertThat;

class CreatePurchaseOrderServiceTest {

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

        CreatePurchaseOrderService service =
                new CreatePurchaseOrderService(
                        catalogGateway,
                        repository
                );

        PurchaseOrder order = service.create(
                new CreatePurchaseOrderCommand(
                        "HEX-BOOK",
                        2
                )
        );

        assertThat(order.productName())
                .isEqualTo("Hexagonal Architecture");
        assertThat(order.total())
                .isEqualByComparingTo("59.80");
        assertThat(repository.findAll())
                .containsExactly(order);
    }

    private static final class FakePurchaseOrderRepository
            implements PurchaseOrderRepository {

        private final List<PurchaseOrder> orders =
                new ArrayList<>();

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
