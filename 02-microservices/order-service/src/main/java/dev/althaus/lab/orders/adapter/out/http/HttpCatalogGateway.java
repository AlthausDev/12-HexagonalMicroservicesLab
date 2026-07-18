package dev.althaus.lab.orders.adapter.out.http;

import dev.althaus.lab.orders.application.port.out.CatalogGateway;
import dev.althaus.lab.orders.domain.ProductSnapshot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class HttpCatalogGateway implements CatalogGateway {

    private final RestClient restClient;

    public HttpCatalogGateway(
            RestClient.Builder builder,
            @Value("${catalog.base-url}") String catalogBaseUrl
    ) {
        this.restClient = builder
                .baseUrl(catalogBaseUrl)
                .build();
    }

    @Override
    public Optional<ProductSnapshot> findBySku(String sku) {
        try {
            ProductResponse response = restClient.get()
                    .uri("/api/products/{sku}", sku)
                    .retrieve()
                    .body(ProductResponse.class);

            if (response == null) {
                return Optional.empty();
            }

            return Optional.of(new ProductSnapshot(
                    response.sku(),
                    response.name(),
                    response.price()
            ));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    private record ProductResponse(
            String sku,
            String name,
            BigDecimal price
    ) {
    }
}
