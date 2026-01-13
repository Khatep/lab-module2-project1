package org.kaspi.labmodule2project1.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.kaspi.labmodule2project1.clients.DeliveryServiceClient;
import org.kaspi.labmodule2project1.domain.dto.ProductDto;
import org.kaspi.labmodule2project1.domain.dto.request.CreateDeliveryForProductRequestDto;
import org.kaspi.labmodule2project1.domain.exceptions.ProductNotFoundException;
import org.kaspi.labmodule2project1.domain.models.OutboxEvent;
import org.kaspi.labmodule2project1.domain.models.Product;
import org.kaspi.labmodule2project1.mappers.ProductMapper;
import org.kaspi.labmodule2project1.repositories.OutboxEventRepository;
import org.kaspi.labmodule2project1.repositories.ProductRepository;
import org.kaspi.labmodule2project1.services.OutboxService;
import org.kaspi.labmodule2project1.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;
    private final OutboxService outboxService;

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

    @Transactional
    public Long createProduct(ProductDto dto) {
        Product product = ProductMapper.toEntity(dto);
        productRepository.save(product);

        CreateDeliveryForProductRequestDto payload =
                new CreateDeliveryForProductRequestDto(product.getId(), product.getAddress());

        OutboxEvent outboxEvent = OutboxEvent.builder()
                .aggregateType("Product")
                .aggregateId(product.getId())
                .eventType("ProductCreated")
                .payload(writeJson(payload))
                .build();

        outboxService.saveOutboxEvent(outboxEvent);

        return product.getId();
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

    private String writeJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot serialize outbox payload", e);
        }
    }
}
