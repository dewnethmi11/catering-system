package com.delightcatering.catering_system.repository;

import com.delightcatering.catering_system.entity.EventBooking;
import com.delightcatering.catering_system.entity.BookingStatus;
import com.delightcatering.catering_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventBookingRepository extends JpaRepository<EventBooking, Long> {
    List<EventBooking> findByCustomer(User customer);
    List<EventBooking> findByStatus(BookingStatus status);
    List<EventBooking> findByEventDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT b FROM EventBooking b WHERE b.eventDate >= ?1 ORDER BY b.eventDate ASC")
    List<EventBooking> findUpcomingBookings(LocalDateTime currentDate);

    @Query("SELECT b FROM EventBooking b WHERE b.customer = ?1 ORDER BY b.createdAt DESC")
    List<EventBooking> findByCustomerOrderByCreatedAtDesc(User customer);
}