package org.sekiro.InventoryManagementSystem.repository;

import org.sekiro.InventoryManagementSystem.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
