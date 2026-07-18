package dev.althaus.lab.orders.adapter.out.memory;

import dev.althaus.lab.orders.application.port.out.PurchaseOrderRepository;
import dev.althaus.lab.orders.domain.PurchaseOrder;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class InMemoryPurchaseOrderRepository implements PurchaseOrderRepository {

    private final ConcurrentMap<UUID, PurchaseOrder> orders = new ConcurrentHashMap<>();

    @Override
    public PurchaseOrder save(PurchaseOrder order) {
        orders.put(order.id(), order);
        return order;
    }

    @Override
    public List<PurchaseOrder> findAll() {
        return orders.values().stream()
                .sorted(Comparator.comparing(PurchaseOrder::createdAt))
                .toList();
    }
}
