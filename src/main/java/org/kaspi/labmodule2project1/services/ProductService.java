package org.kaspi.labmodule2project1.services;

import org.kaspi.labmodule2project1.domain.dto.ProductDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductService {

    ProductDto getById(Long id);

    List<ProductDto> getAll();

    CompletableFuture<Long> createProduct(ProductDto dto);

    void update(Long id, ProductDto dto);

    void delete(Long id);
}
