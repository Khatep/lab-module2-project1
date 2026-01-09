package org.kaspi.labmodule2project1.mappers;

import lombok.experimental.UtilityClass;
import org.kaspi.labmodule2project1.domain.dto.ProductDto;
import org.kaspi.labmodule2project1.domain.models.Product;

@UtilityClass
public class ProductMapper {
    public static Product toEntity(ProductDto productDto) {
        return Product.builder()
                .name(productDto.name())
                .price(productDto.price())
                .build();
    }

    public static ProductDto toDto(Product product) {
        return new ProductDto(product.getName(), product.getPrice());
    }
}
