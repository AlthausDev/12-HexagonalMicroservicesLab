package dev.althaus.lab.hexagonal.adapter.in.web;

import dev.althaus.lab.hexagonal.application.port.in.CreateOrderUseCase;
import dev.althaus.lab.hexagonal.application.port.in.QueryOrderUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final QueryOrderUseCase queryOrderUseCase;

    public OrderController(
            CreateOrderUseCase createOrderUseCase,
            QueryOrderUseCase queryOrderUseCase
    ) {
        this.createOrderUseCase = createOrderUseCase;
        this.queryOrderUseCase = queryOrderUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@RequestBody CreateOrderRequest request) {
        return OrderResponse.from(createOrderUseCase.create(request.toCommand()));
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable UUID id) {
        return queryOrderUseCase.findById(id)
                .map(OrderResponse::from)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pedido no encontrado."
                ));
    }

    @GetMapping
    public List<OrderResponse> findAll() {
        return queryOrderUseCase.findAll().stream()
                .map(OrderResponse::from)
                .toList();
    }
}
