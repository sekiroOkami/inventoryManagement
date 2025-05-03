package org.sekiro.InventoryManagementSystem.repository;

import org.sekiro.InventoryManagementSystem.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
