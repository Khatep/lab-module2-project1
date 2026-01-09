package org.kaspi.labmodule2project1.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.kaspi.labmodule2project1.domain.dto.ProductDto;
import org.kaspi.labmodule2project1.domain.exceptions.ProductNotFoundException;
import org.kaspi.labmodule2project1.domain.models.Product;
import org.kaspi.labmodule2project1.mappers.ProductMapper;
import org.kaspi.labmodule2project1.repositories.ProductRepository;
import org.kaspi.labmodule2project1.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + id));

        return ProductMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDto)
                .toList();
    }

    public Long createProduct(ProductDto dto) {
        Product product = ProductMapper.toEntity(dto);
        return productRepository.save(product).getId();
    }

    @Override
    public void update(Long id, ProductDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + id));

        product.setName(dto.name());
        product.setPrice(dto.price());

        productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found: " + id);
        }
        productRepository.deleteById(id);
    }
}
