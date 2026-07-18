package dev.althaus.lab.catalog.application;

import dev.althaus.lab.catalog.domain.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CatalogService {

    private final Map<String, Product> products = Map.of(
            "HEX-BOOK",
            new Product(
                    "HEX-BOOK",
                    "Hexagonal Architecture",
                    new BigDecimal("29.90")
            ),
            "DOCKER-GUIDE",
            new Product(
                    "DOCKER-GUIDE",
                    "Docker from First Principles",
                    new BigDecimal("24.50")
            ),
            "MICRO-LAB",
            new Product(
                    "MICRO-LAB",
                    "Microservices Laboratory",
                    new BigDecimal("34.00")
            )
    );

    public Optional<Product> findBySku(String sku) {
        if (sku == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(products.get(sku.trim().toUpperCase()));
    }

    public List<Product> findAll() {
        return products.values().stream()
                .sorted(Comparator.comparing(Product::sku))
                .toList();
    }
}
