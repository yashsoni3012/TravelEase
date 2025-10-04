package com.travelmanagement.controller;

import com.travelmanagement.entity.TravelPackage;
import com.travelmanagement.service.TravelPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class TravelPackageController {
    
    private final TravelPackageService travelPackageService;
    
    @GetMapping
    public ResponseEntity<List<TravelPackage>> getAllPackages() {
        List<TravelPackage> packages = travelPackageService.getActivePackages();
        return ResponseEntity.ok(packages);
    }
    
    @GetMapping("/featured")
    public ResponseEntity<List<TravelPackage>> getFeaturedPackages() {
        List<TravelPackage> packages = travelPackageService.getFeaturedPackages();
        return ResponseEntity.ok(packages);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TravelPackage> getPackageById(@PathVariable Long id) {
        return travelPackageService.getPackageById(id)
                .map(package_ -> ResponseEntity.ok(package_))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/destination/{destinationId}")
    public ResponseEntity<List<TravelPackage>> getPackagesByDestination(@PathVariable Long destinationId) {
        List<TravelPackage> packages = travelPackageService.getPackagesByDestination(destinationId);
        return ResponseEntity.ok(packages);
    }
    
    @GetMapping("/type/{packageType}")
    public ResponseEntity<List<TravelPackage>> getPackagesByType(@PathVariable TravelPackage.PackageType packageType) {
        List<TravelPackage> packages = travelPackageService.getPackagesByType(packageType);
        return ResponseEntity.ok(packages);
    }
    
    @GetMapping("/available")
    public ResponseEntity<List<TravelPackage>> getAvailablePackages(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        List<TravelPackage> packages = travelPackageService.getAvailablePackages(startDate);
        return ResponseEntity.ok(packages);
    }
    
    @GetMapping("/available-space")
    public ResponseEntity<List<TravelPackage>> getAvailablePackagesWithSpace() {
        List<TravelPackage> packages = travelPackageService.getAvailablePackagesWithSpace();
        return ResponseEntity.ok(packages);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<TravelPackage>> searchPackages(@RequestParam String q) {
        List<TravelPackage> packages = travelPackageService.searchPackages(q);
        return ResponseEntity.ok(packages);
    }
    
    @GetMapping("/price-range")
    public ResponseEntity<List<TravelPackage>> getPackagesByPriceRange(
            @RequestParam Double minPrice, 
            @RequestParam Double maxPrice) {
        List<TravelPackage> packages = travelPackageService.getPackagesByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(packages);
    }
    
    @PostMapping
    public ResponseEntity<TravelPackage> createPackage(@RequestBody TravelPackage travelPackage) {
        TravelPackage createdPackage = travelPackageService.createPackage(travelPackage);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPackage);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TravelPackage> updatePackage(@PathVariable Long id, @RequestBody TravelPackage travelPackage) {
        try {
            TravelPackage updatedPackage = travelPackageService.updatePackage(id, travelPackage);
            return ResponseEntity.ok(updatedPackage);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        try {
            travelPackageService.deletePackage(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<TravelPackage> togglePackageStatus(@PathVariable Long id) {
        try {
            travelPackageService.togglePackageStatus(id);
            return travelPackageService.getPackageById(id)
                    .map(package_ -> ResponseEntity.ok(package_))
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
