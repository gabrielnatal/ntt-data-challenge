package com.example.productservice.web;

import com.example.productservice.model.Product;
import com.example.productservice.repo.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Product> all() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> byId(@PathVariable Long id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product p) {
        Product saved = repository.save(p);
        return ResponseEntity.created(URI.create("/products/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id,
                                          @Valid @RequestBody Product p) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(p.getName());
                    existing.setDescription(p.getDescription());
                    existing.setPrice(p.getPrice());
                    Product saved = repository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> {
                    p.setId(id);
                    Product saved = repository.save(p);
                    return ResponseEntity.ok(saved);
                });
    }
}
