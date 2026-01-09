package org.kaspi.labmodule2project1.repositories;

import org.kaspi.labmodule2project1.domain.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
