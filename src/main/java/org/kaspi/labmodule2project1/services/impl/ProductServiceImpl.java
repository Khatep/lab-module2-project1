package org.kaspi.labmodule2project1.services.impl;

import lombok.RequiredArgsConstructor;
import org.kaspi.labmodule2project1.domain.dto.ProductDto;
import org.kaspi.labmodule2project1.domain.exceptions.ProductNotFoundException;
import org.kaspi.labmodule2project1.domain.models.Product;
import org.kaspi.labmodule2project1.mappers.ProductMapper;
import org.kaspi.labmodule2project1.repositories.ProductRepository;
import org.kaspi.labmodule2project1.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + id));

        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Long createProduct(ProductDto dto) {
        Product product = productMapper.toEntity(dto);
        //todo: save in outbox table
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
