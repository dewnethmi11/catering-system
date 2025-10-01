package com.delightcatering.catering_system.controller;

import com.delightcatering.catering_system.entity.MenuItem;
import com.delightcatering.catering_system.entity.MenuCategory;
import com.delightcatering.catering_system.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
@CrossOrigin(origins = "*")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        List<MenuItem> menuItems = menuItemService.getAllMenuItems();
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        return menuItemService.getMenuItemById(id)
                .map(menuItem -> ResponseEntity.ok().body(menuItem))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MenuItem> createMenuItem(@Valid @RequestBody MenuItem menuItem) {
        MenuItem createdMenuItem = menuItemService.createMenuItem(menuItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenuItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long id, @Valid @RequestBody MenuItem menuItemDetails) {
        try {
            MenuItem updatedMenuItem = menuItemService.updateMenuItem(id, menuItemDetails);
            return ResponseEntity.ok(updatedMenuItem);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable Long id) {
        try {
            menuItemService.deleteMenuItem(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByCategory(@PathVariable MenuCategory category) {
        List<MenuItem> menuItems = menuItemService.getMenuItemsByCategory(category);
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/available")
    public ResponseEntity<List<MenuItem>> getAvailableMenuItems() {
        List<MenuItem> menuItems = menuItemService.getAvailableMenuItems();
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/vegetarian")
    public ResponseEntity<List<MenuItem>> getVegetarianItems() {
        List<MenuItem> menuItems = menuItemService.getVegetarianItems();
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/vegan")
    public ResponseEntity<List<MenuItem>> getVeganItems() {
        List<MenuItem> menuItems = menuItemService.getVeganItems();
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/gluten-free")
    public ResponseEntity<List<MenuItem>> getGlutenFreeItems() {
        List<MenuItem> menuItems = menuItemService.getGlutenFreeItems();
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MenuItem>> searchMenuItems(@RequestParam String name) {
        List<MenuItem> menuItems = menuItemService.searchMenuItemsByName(name);
        return ResponseEntity.ok(menuItems);
    }

    @PutMapping("/{id}/toggle-availability")
    public ResponseEntity<MenuItem> toggleAvailability(@PathVariable Long id) {
        try {
            MenuItem menuItem = menuItemService.toggleAvailability(id);
            return ResponseEntity.ok(menuItem);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}