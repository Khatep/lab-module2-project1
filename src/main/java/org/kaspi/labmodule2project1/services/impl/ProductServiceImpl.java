package org.kaspi.labmodule2project1.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kaspi.labmodule2project1.clients.DeliveryServiceClient;
import org.kaspi.labmodule2project1.domain.dto.ProductDto;
import org.kaspi.labmodule2project1.domain.dto.request.CreateDeliveryForProductRequestDto;
import org.kaspi.labmodule2project1.domain.enums.DeliveryStatus;
import org.kaspi.labmodule2project1.domain.exceptions.ProductNotFoundException;
import org.kaspi.labmodule2project1.domain.models.Product;
import org.kaspi.labmodule2project1.mappers.ProductMapper;
import org.kaspi.labmodule2project1.repositories.ProductRepository;
import org.kaspi.labmodule2project1.services.ProductService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final DeliveryServiceClient deliveryServiceClient;

    @Override
    public Mono<ProductDto> getById(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found: " + id)))
                .map(ProductMapper::toDto);
    }

    @Override
    public Flux<ProductDto> getAll() {
        return productRepository.findAll()
                .map(ProductMapper::toDto);
    }

    @Override
    public Mono<Long> createProduct(ProductDto dto) {
        Product product = ProductMapper.toEntity(dto);

        return productRepository.save(product)
                .flatMap(savedProduct ->
                        deliveryServiceClient
                                .createDelivery(
                                        new CreateDeliveryForProductRequestDto(
                                                savedProduct.getId(),
                                                product.getAddress(),
                                                DeliveryStatus.CREATED
                                        )
                                )
                                .thenReturn(savedProduct.getId())
                );
    }

    @Override
    public Mono<Void> update(Long id, ProductDto dto) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found: " + id)))
                .flatMap(product -> {
                    product.setName(dto.name());
                    product.setPrice(dto.price());
                    return productRepository.save(product).then();
                });
    }

    @Override
    public Mono<Void> delete(Long id) {
        return productRepository.existsById(id)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new ProductNotFoundException("Product not found: " + id));
                    }
                    return productRepository.deleteById(id);
                });
    }
}
