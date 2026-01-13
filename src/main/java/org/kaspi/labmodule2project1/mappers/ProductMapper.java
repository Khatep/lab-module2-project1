package org.kaspi.labmodule2project1.mappers;

import org.kaspi.labmodule2project1.domain.dto.ProductDto;
import org.kaspi.labmodule2project1.domain.models.Product;

public class ProductMapper {

    private ProductMapper() {

    }

    public static ProductDto toDto(Product product) {
        if (product == null) return null;

        return new ProductDto(
                product.getName(),
                product.getPrice(),
                product.getAddress()
        );
    }

    public static Product toEntity(ProductDto dto) {
        if (dto == null) return null;

        return Product.builder()
                .name(dto.name())
                .price(dto.price())
                .address(dto.address())
                .build();
    }
}
