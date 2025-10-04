package com.travelmanagement.repository;

import com.travelmanagement.entity.TravelPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TravelPackageRepository extends JpaRepository<TravelPackage, Long> {
    
    List<TravelPackage> findByIsActiveTrue();
    
    List<TravelPackage> findByIsFeaturedTrueAndIsActiveTrue();
    
    List<TravelPackage> findByDestinationId(Long destinationId);
    
    List<TravelPackage> findByPackageType(TravelPackage.PackageType packageType);
    
    @Query("SELECT tp FROM TravelPackage tp WHERE tp.startDate >= :startDate AND tp.isActive = true")
    List<TravelPackage> findAvailablePackages(@Param("startDate") LocalDate startDate);
    
    @Query("SELECT tp FROM TravelPackage tp WHERE " +
           "tp.price BETWEEN :minPrice AND :maxPrice AND tp.isActive = true")
    List<TravelPackage> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);
    
    @Query("SELECT tp FROM TravelPackage tp WHERE " +
           "LOWER(tp.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(tp.destination.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(tp.destination.country) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<TravelPackage> searchPackages(@Param("searchTerm") String searchTerm);
    
    @Query("SELECT tp FROM TravelPackage tp WHERE " +
           "tp.currentParticipants < tp.maxParticipants AND tp.isActive = true")
    List<TravelPackage> findAvailablePackagesWithSpace();
    
}
