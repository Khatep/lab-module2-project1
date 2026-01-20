package org.kaspi.labmodule2project1.controllers;

import lombok.RequiredArgsConstructor;
import org.kaspi.labmodule2project1.domain.dto.ProductDto;
import org.kaspi.labmodule2project1.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping(ProductController.PATH)
@RequiredArgsConstructor
public class ProductController {

    protected static final String PATH = "api/v1/products";
    private final ProductService productService;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> getById(@PathVariable Long id) {
        return productService.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<ProductDto> getAll() {
        return productService.getAll();
    }

    @PostMapping
    public Mono<ResponseEntity<Long>> createProduct(@RequestBody ProductDto dto) {
        return productService.createProduct(dto)
                .map(id -> ResponseEntity
                        .created(URI.create(PATH + "/" + id))
                        .body(id)
                );
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Void>> update(@PathVariable Long id,
                                             @RequestBody ProductDto dto) {
        return productService.update(id, dto)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return productService.delete(id)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
