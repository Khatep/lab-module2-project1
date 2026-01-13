package org.kaspi.labmodule2project1.mappers;

import org.kaspi.labmodule2project1.domain.dto.ProductDto;
import org.kaspi.labmodule2project1.domain.models.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);

    Product toEntity(ProductDto dto);
}
