package com.delightcatering.catering_system.controller;

import com.delightcatering.catering_system.entity.EventBooking;
import com.delightcatering.catering_system.entity.BookingStatus;
import com.delightcatering.catering_system.service.EventBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class EventBookingController {

    @Autowired
    private EventBookingService bookingService;

    @GetMapping
    public ResponseEntity<List<EventBooking>> getAllBookings() {
        List<EventBooking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventBooking> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(booking -> ResponseEntity.ok().body(booking))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventBooking> createBooking(@Valid @RequestBody EventBooking booking) {
        EventBooking createdBooking = bookingService.createBooking(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventBooking> updateBooking(@PathVariable Long id, @Valid @RequestBody EventBooking bookingDetails) {
        try {
            EventBooking updatedBooking = bookingService.updateBooking(id, bookingDetails);
            return ResponseEntity.ok(updatedBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
        try {
            bookingService.deleteBooking(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<EventBooking>> getBookingsByStatus(@PathVariable BookingStatus status) {
        List<EventBooking> bookings = bookingService.getBookingsByStatus(status);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<EventBooking>> getUpcomingBookings() {
        List<EventBooking> bookings = bookingService.getUpcomingBookings();
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EventBooking> updateBookingStatus(@PathVariable Long id, @RequestParam BookingStatus status) {
        try {
            EventBooking booking = bookingService.updateBookingStatus(id, status);
            return ResponseEntity.ok(booking);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}