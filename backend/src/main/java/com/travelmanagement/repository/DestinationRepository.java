package com.travelmanagement.repository;

import com.travelmanagement.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    
    List<Destination> findByIsActiveTrue();
    
    List<Destination> findByIsFeaturedTrueAndIsActiveTrue();
    
    List<Destination> findByCountry(String country);
    
    List<Destination> findByCity(String city);
    
    @Query("SELECT d FROM Destination d WHERE " +
           "LOWER(d.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(d.country) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(d.city) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Destination> searchDestinations(@Param("searchTerm") String searchTerm);
    
    @Query("SELECT d FROM Destination d WHERE d.price BETWEEN :minPrice AND :maxPrice AND d.isActive = true")
    List<Destination> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);
    
}
