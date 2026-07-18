package dev.althaus.lab.orders.adapter.in.web;

import dev.althaus.lab.orders.application.port.in.CreatePurchaseOrderUseCase;
import dev.althaus.lab.orders.application.port.in.QueryPurchaseOrderUseCase;
import dev.althaus.lab.orders.domain.PurchaseOrder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class PurchaseOrderController {

    private final CreatePurchaseOrderUseCase createOrderUseCase;
    private final QueryPurchaseOrderUseCase queryOrderUseCase;

    public PurchaseOrderController(
            CreatePurchaseOrderUseCase createOrderUseCase,
            QueryPurchaseOrderUseCase queryOrderUseCase
    ) {
        this.createOrderUseCase = createOrderUseCase;
        this.queryOrderUseCase = queryOrderUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseOrder create(@RequestBody CreatePurchaseOrderRequest request) {
        return createOrderUseCase.create(request.toCommand());
    }

    @GetMapping
    public List<PurchaseOrder> findAll() {
        return queryOrderUseCase.findAll();
    }
}
