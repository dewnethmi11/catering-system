package com.delightcatering.catering_system.service;

import com.delightcatering.catering_system.entity.EventBooking;
import com.delightcatering.catering_system.entity.BookingStatus;
import com.delightcatering.catering_system.entity.User;
import com.delightcatering.catering_system.repository.EventBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventBookingService {

    @Autowired
    private EventBookingRepository bookingRepository;

    public List<EventBooking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<EventBooking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public EventBooking createBooking(EventBooking booking) {
        return bookingRepository.save(booking);
    }

    public EventBooking updateBooking(Long id, EventBooking bookingDetails) {
        EventBooking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        booking.setEventDate(bookingDetails.getEventDate());
        booking.setEventVenue(bookingDetails.getEventVenue());
        booking.setVenueAddress(bookingDetails.getVenueAddress());
        booking.setGuestCount(bookingDetails.getGuestCount());
        booking.setTotalAmount(bookingDetails.getTotalAmount());
        booking.setSpecialRequests(bookingDetails.getSpecialRequests());
        booking.setContactPersonName(bookingDetails.getContactPersonName());
        booking.setContactPersonPhone(bookingDetails.getContactPersonPhone());

        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }

    public List<EventBooking> getBookingsByCustomer(User customer) {
        return bookingRepository.findByCustomer(customer);
    }

    public List<EventBooking> getBookingsByStatus(BookingStatus status) {
        return bookingRepository.findByStatus(status);
    }

    public List<EventBooking> getUpcomingBookings() {
        return bookingRepository.findUpcomingBookings(LocalDateTime.now());
    }

    public EventBooking updateBookingStatus(Long id, BookingStatus status) {
        EventBooking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    public List<EventBooking> getBookingsByDateRange(LocalDateTime start, LocalDateTime end) {
        return bookingRepository.findByEventDateBetween(start, end);
    }
}