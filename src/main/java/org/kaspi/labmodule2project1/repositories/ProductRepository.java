package org.kaspi.labmodule2project1.repositories;

import org.kaspi.labmodule2project1.domain.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
