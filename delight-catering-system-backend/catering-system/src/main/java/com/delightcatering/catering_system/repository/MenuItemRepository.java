package com.delightcatering.catering_system.repository;

import com.delightcatering.catering_system.entity.MenuItem;
import com.delightcatering.catering_system.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByCategory(MenuCategory category);
    List<MenuItem> findByAvailableTrue();
    List<MenuItem> findByNameContainingIgnoreCase(String name);
    List<MenuItem> findByVegetarianTrue();
    List<MenuItem> findByVeganTrue();
    List<MenuItem> findByGlutenFreeTrue();
}