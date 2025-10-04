package com.travelmanagement.controller;

import com.travelmanagement.entity.Destination;
import com.travelmanagement.service.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class DestinationController {
    
    private final DestinationService destinationService;
    
    @GetMapping
    public ResponseEntity<List<Destination>> getAllDestinations() {
        List<Destination> destinations = destinationService.getActiveDestinations();
        return ResponseEntity.ok(destinations);
    }
    
    @GetMapping("/featured")
    public ResponseEntity<List<Destination>> getFeaturedDestinations() {
        List<Destination> destinations = destinationService.getFeaturedDestinations();
        return ResponseEntity.ok(destinations);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Destination> getDestinationById(@PathVariable Long id) {
        return destinationService.getDestinationById(id)
                .map(destination -> ResponseEntity.ok(destination))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/country/{country}")
    public ResponseEntity<List<Destination>> getDestinationsByCountry(@PathVariable String country) {
        List<Destination> destinations = destinationService.getDestinationsByCountry(country);
        return ResponseEntity.ok(destinations);
    }
    
    @GetMapping("/city/{city}")
    public ResponseEntity<List<Destination>> getDestinationsByCity(@PathVariable String city) {
        List<Destination> destinations = destinationService.getDestinationsByCity(city);
        return ResponseEntity.ok(destinations);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Destination>> searchDestinations(@RequestParam String q) {
        List<Destination> destinations = destinationService.searchDestinations(q);
        return ResponseEntity.ok(destinations);
    }
    
    @GetMapping("/price-range")
    public ResponseEntity<List<Destination>> getDestinationsByPriceRange(
            @RequestParam Double minPrice, 
            @RequestParam Double maxPrice) {
        List<Destination> destinations = destinationService.getDestinationsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(destinations);
    }
    
    @PostMapping
    public ResponseEntity<Destination> createDestination(@RequestBody Destination destination) {
        Destination createdDestination = destinationService.createDestination(destination);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDestination);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Destination> updateDestination(@PathVariable Long id, @RequestBody Destination destination) {
        try {
            Destination updatedDestination = destinationService.updateDestination(id, destination);
            return ResponseEntity.ok(updatedDestination);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        try {
            destinationService.deleteDestination(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<Destination> toggleDestinationStatus(@PathVariable Long id) {
        try {
            destinationService.toggleDestinationStatus(id);
            return destinationService.getDestinationById(id)
                    .map(destination -> ResponseEntity.ok(destination))
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
