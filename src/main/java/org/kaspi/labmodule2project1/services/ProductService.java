package org.kaspi.labmodule2project1.services;

import org.kaspi.labmodule2project1.domain.dto.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<ProductDto> getById(Long id);

    Flux<ProductDto> getAll();

    Mono<Long> createProduct(ProductDto dto);

    Mono<Void> update(Long id, ProductDto dto);

    Mono<Void> delete(Long id);
}
