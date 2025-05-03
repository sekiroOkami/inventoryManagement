package org.sekiro.InventoryManagementSystem.repository;

import org.sekiro.InventoryManagementSystem.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
