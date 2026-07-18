package dev.althaus.lab.catalog.adapter.in.web;

import dev.althaus.lab.catalog.application.CatalogService;
import dev.althaus.lab.catalog.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final CatalogService catalogService;

    public ProductController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/{sku}")
    public Product findBySku(@PathVariable String sku) {
        return catalogService.findBySku(sku)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Producto no encontrado."
                ));
    }

    @GetMapping
    public List<Product> findAll() {
        return catalogService.findAll();
    }
}
