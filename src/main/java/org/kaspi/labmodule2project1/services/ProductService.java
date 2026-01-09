package org.kaspi.labmodule2project1.services;

import org.kaspi.labmodule2project1.domain.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto getById(Long id);

    List<ProductDto> getAll();

    Long createProduct(ProductDto dto);

    void update(Long id, ProductDto dto);

    void delete(Long id);
}
