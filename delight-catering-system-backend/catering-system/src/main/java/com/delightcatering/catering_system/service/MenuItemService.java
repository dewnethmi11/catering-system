package com.delightcatering.catering_system.service;

import com.delightcatering.catering_system.entity.MenuItem;
import com.delightcatering.catering_system.entity.MenuCategory;
import com.delightcatering.catering_system.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public Optional<MenuItem> getMenuItemById(Long id) {
        return menuItemRepository.findById(id);
    }

    public MenuItem createMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public MenuItem updateMenuItem(Long id, MenuItem menuItemDetails) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + id));

        menuItem.setName(menuItemDetails.getName());
        menuItem.setDescription(menuItemDetails.getDescription());
        menuItem.setPrice(menuItemDetails.getPrice());
        menuItem.setCategory(menuItemDetails.getCategory());
        menuItem.setImageUrl(menuItemDetails.getImageUrl());
        menuItem.setIngredients(menuItemDetails.getIngredients());
        menuItem.setVegetarian(menuItemDetails.isVegetarian());
        menuItem.setVegan(menuItemDetails.isVegan());
        menuItem.setGlutenFree(menuItemDetails.isGlutenFree());
        menuItem.setPreparationTimeMinutes(menuItemDetails.getPreparationTimeMinutes());

        return menuItemRepository.save(menuItem);
    }

    public void deleteMenuItem(Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new RuntimeException("Menu item not found with id: " + id);
        }
        menuItemRepository.deleteById(id);
    }

    public List<MenuItem> getMenuItemsByCategory(MenuCategory category) {
        return menuItemRepository.findByCategory(category);
    }

    public List<MenuItem> getAvailableMenuItems() {
        return menuItemRepository.findByAvailableTrue();
    }

    public List<MenuItem> searchMenuItemsByName(String name) {
        return menuItemRepository.findByNameContainingIgnoreCase(name);
    }

    public List<MenuItem> getVegetarianItems() {
        return menuItemRepository.findByVegetarianTrue();
    }

    public List<MenuItem> getVeganItems() {
        return menuItemRepository.findByVeganTrue();
    }

    public List<MenuItem> getGlutenFreeItems() {
        return menuItemRepository.findByGlutenFreeTrue();
    }

    public MenuItem toggleAvailability(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + id));
        menuItem.setAvailable(!menuItem.isAvailable());
        return menuItemRepository.save(menuItem);
    }
}