package com.delightcatering.catering_system.controller;

import com.delightcatering.catering_system.entity.Staff;
import com.delightcatering.catering_system.entity.StaffRole;
import com.delightcatering.catering_system.entity.EmploymentStatus;
import com.delightcatering.catering_system.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin(origins = "*")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping
    public ResponseEntity<List<Staff>> getAllStaff() {
        List<Staff> staff = staffService.getAllStaff();
        return ResponseEntity.ok(staff);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Long id) {
        return staffService.getStaffById(id)
                .map(staff -> ResponseEntity.ok().body(staff))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Staff> createStaff(@Valid @RequestBody Staff staff) {
        Staff createdStaff = staffService.createStaff(staff);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStaff);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable Long id, @Valid @RequestBody Staff staffDetails) {
        try {
            Staff updatedStaff = staffService.updateStaff(id, staffDetails);
            return ResponseEntity.ok(updatedStaff);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStaff(@PathVariable Long id) {
        try {
            staffService.deleteStaff(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<Staff>> getStaffByRole(@PathVariable StaffRole role) {
        List<Staff> staff = staffService.getStaffByRole(role);
        return ResponseEntity.ok(staff);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Staff>> getAvailableStaff() {
        List<Staff> staff = staffService.getAvailableStaff();
        return ResponseEntity.ok(staff);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Staff>> getActiveStaff() {
        List<Staff> staff = staffService.getActiveStaff();
        return ResponseEntity.ok(staff);
    }

    @PutMapping("/{id}/employment-status")
    public ResponseEntity<Staff> updateEmploymentStatus(@PathVariable Long id, @RequestParam EmploymentStatus status) {
        try {
            Staff staff = staffService.updateEmploymentStatus(id, status);
            return ResponseEntity.ok(staff);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/toggle-availability")
    public ResponseEntity<Staff> toggleAvailability(@PathVariable Long id) {
        try {
            Staff staff = staffService.toggleAvailability(id);
            return ResponseEntity.ok(staff);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}