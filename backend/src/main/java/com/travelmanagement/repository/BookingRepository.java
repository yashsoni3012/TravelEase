package com.travelmanagement.repository;

import com.travelmanagement.entity.Booking;
import com.travelmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    List<Booking> findByUser(User user);
    
    List<Booking> findByUserId(Long userId);
    
    Optional<Booking> findByBookingReference(String bookingReference);
    
    List<Booking> findByBookingStatus(Booking.BookingStatus bookingStatus);
    
    List<Booking> findByPaymentStatus(Booking.PaymentStatus paymentStatus);
    
    @Query("SELECT b FROM Booking b WHERE b.bookingDate BETWEEN :startDate AND :endDate")
    List<Booking> findByBookingDateRange(@Param("startDate") LocalDateTime startDate, 
                                        @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.travelPackage.id = :packageId AND b.bookingStatus = 'CONFIRMED'")
    Long countConfirmedBookingsByPackageId(@Param("packageId") Long packageId);
    
    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.bookingStatus = 'CONFIRMED'")
    List<Booking> findConfirmedBookingsByUserId(@Param("userId") Long userId);
    
}
