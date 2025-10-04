package com.travelmanagement.service;

import com.travelmanagement.entity.Booking;
import com.travelmanagement.entity.TravelPackage;
import com.travelmanagement.entity.User;
import com.travelmanagement.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {
    
    private final BookingRepository bookingRepository;
    private final TravelPackageService travelPackageService;
    private final UserService userService;
    
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    
    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
    
    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }
    
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }
    
    public Optional<Booking> getBookingByReference(String bookingReference) {
        return bookingRepository.findByBookingReference(bookingReference);
    }
    
    public List<Booking> getBookingsByStatus(Booking.BookingStatus status) {
        return bookingRepository.findByBookingStatus(status);
    }
    
    public List<Booking> getBookingsByPaymentStatus(Booking.PaymentStatus status) {
        return bookingRepository.findByPaymentStatus(status);
    }
    
    public List<Booking> getBookingsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return bookingRepository.findByBookingDateRange(startDate, endDate);
    }
    
    public List<Booking> getConfirmedBookingsByUser(Long userId) {
        return bookingRepository.findConfirmedBookingsByUserId(userId);
    }
    
    public Booking createBooking(Booking booking) {
        // Validate travel package availability
        TravelPackage travelPackage = travelPackageService.getPackageById(booking.getTravelPackage().getId())
                .orElseThrow(() -> new RuntimeException("Travel package not found"));
        
        if (!travelPackage.getIsActive()) {
            throw new RuntimeException("Travel package is not available");
        }
        
        // Check if there's enough space
        Long confirmedBookings = bookingRepository.countConfirmedBookingsByPackageId(travelPackage.getId());
        int availableSpace = travelPackage.getMaxParticipants() - confirmedBookings.intValue();
        
        if (booking.getNumberOfParticipants() > availableSpace) {
            throw new RuntimeException("Not enough space available for this package");
        }
        
        // Calculate total price
        double totalPrice = travelPackage.getPrice() * booking.getNumberOfParticipants();
        booking.setTotalPrice(totalPrice);
        booking.setCurrency(travelPackage.getCurrency());
        
        // Set user
        User user = userService.getUserById(booking.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        booking.setUser(user);
        
        return bookingRepository.save(booking);
    }
    
    public Booking updateBooking(Long id, Booking bookingDetails) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        
        booking.setNumberOfParticipants(bookingDetails.getNumberOfParticipants());
        booking.setSpecialRequests(bookingDetails.getSpecialRequests());
        
        // Recalculate total price if participants changed
        if (!booking.getNumberOfParticipants().equals(bookingDetails.getNumberOfParticipants())) {
            double totalPrice = booking.getTravelPackage().getPrice() * bookingDetails.getNumberOfParticipants();
            booking.setTotalPrice(totalPrice);
        }
        
        return bookingRepository.save(booking);
    }
    
    public Booking updateBookingStatus(Long id, Booking.BookingStatus status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        
        booking.setBookingStatus(status);
        return bookingRepository.save(booking);
    }
    
    public Booking updatePaymentStatus(Long id, Booking.PaymentStatus status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        
        booking.setPaymentStatus(status);
        
        // If payment is confirmed, update booking status to confirmed
        if (status == Booking.PaymentStatus.PAID && booking.getBookingStatus() == Booking.BookingStatus.PENDING) {
            booking.setBookingStatus(Booking.BookingStatus.CONFIRMED);
        }
        
        return bookingRepository.save(booking);
    }
    
    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        
        if (booking.getBookingStatus() == Booking.BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking is already cancelled");
        }
        
        booking.setBookingStatus(Booking.BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
    
    public void deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        bookingRepository.delete(booking);
    }
}
