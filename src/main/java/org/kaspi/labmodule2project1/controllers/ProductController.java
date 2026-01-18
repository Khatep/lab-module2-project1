package org.kaspi.labmodule2project1.controllers;

import lombok.RequiredArgsConstructor;
import org.kaspi.labmodule2project1.domain.dto.ProductDto;
import org.kaspi.labmodule2project1.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = ProductController.PATH)
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    protected static final String PATH = "api/v1/products";

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<Long>> createProduct(@RequestBody ProductDto dto) {
        return productService.createProduct(dto)
                .thenApply(id ->
                        ResponseEntity
                                .created(URI.create(PATH + "/" + id))
                                .body(id)
                );
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody ProductDto productDto
    ) {
        productService.update(id, productDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
