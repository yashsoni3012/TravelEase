package com.travelmanagement.service;

import com.travelmanagement.entity.TravelPackage;
import com.travelmanagement.repository.TravelPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TravelPackageService {
    
    private final TravelPackageRepository travelPackageRepository;
    
    public List<TravelPackage> getAllPackages() {
        return travelPackageRepository.findAll();
    }
    
    public List<TravelPackage> getActivePackages() {
        return travelPackageRepository.findByIsActiveTrue();
    }
    
    public List<TravelPackage> getFeaturedPackages() {
        return travelPackageRepository.findByIsFeaturedTrueAndIsActiveTrue();
    }
    
    public Optional<TravelPackage> getPackageById(Long id) {
        return travelPackageRepository.findById(id);
    }
    
    public List<TravelPackage> getPackagesByDestination(Long destinationId) {
        return travelPackageRepository.findByDestinationId(destinationId);
    }
    
    public List<TravelPackage> getPackagesByType(TravelPackage.PackageType packageType) {
        return travelPackageRepository.findByPackageType(packageType);
    }
    
    public List<TravelPackage> getAvailablePackages(LocalDate startDate) {
        return travelPackageRepository.findAvailablePackages(startDate);
    }
    
    public List<TravelPackage> getPackagesByPriceRange(Double minPrice, Double maxPrice) {
        return travelPackageRepository.findByPriceRange(minPrice, maxPrice);
    }
    
    public List<TravelPackage> searchPackages(String searchTerm) {
        return travelPackageRepository.searchPackages(searchTerm);
    }
    
    public List<TravelPackage> getAvailablePackagesWithSpace() {
        return travelPackageRepository.findAvailablePackagesWithSpace();
    }
    
    public TravelPackage createPackage(TravelPackage travelPackage) {
        return travelPackageRepository.save(travelPackage);
    }
    
    public TravelPackage updatePackage(Long id, TravelPackage packageDetails) {
        TravelPackage travelPackage = travelPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Travel package not found with id: " + id));
        
        travelPackage.setName(packageDetails.getName());
        travelPackage.setDescription(packageDetails.getDescription());
        travelPackage.setDestination(packageDetails.getDestination());
        travelPackage.setStartDate(packageDetails.getStartDate());
        travelPackage.setEndDate(packageDetails.getEndDate());
        travelPackage.setPrice(packageDetails.getPrice());
        travelPackage.setCurrency(packageDetails.getCurrency());
        travelPackage.setMaxParticipants(packageDetails.getMaxParticipants());
        travelPackage.setPackageType(packageDetails.getPackageType());
        travelPackage.setIncludes(packageDetails.getIncludes());
        travelPackage.setExcludes(packageDetails.getExcludes());
        travelPackage.setItinerary(packageDetails.getItinerary());
        travelPackage.setIsFeatured(packageDetails.getIsFeatured());
        travelPackage.setIsActive(packageDetails.getIsActive());
        
        return travelPackageRepository.save(travelPackage);
    }
    
    public void deletePackage(Long id) {
        TravelPackage travelPackage = travelPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Travel package not found with id: " + id));
        travelPackageRepository.delete(travelPackage);
    }
    
    public void togglePackageStatus(Long id) {
        TravelPackage travelPackage = travelPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Travel package not found with id: " + id));
        travelPackage.setIsActive(!travelPackage.getIsActive());
        travelPackageRepository.save(travelPackage);
    }
    
    public void updateParticipantCount(Long packageId, int participants) {
        TravelPackage travelPackage = travelPackageRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Travel package not found with id: " + packageId));
        
        int newCount = travelPackage.getCurrentParticipants() + participants;
        if (newCount > travelPackage.getMaxParticipants()) {
            throw new RuntimeException("Not enough space available for this package");
        }
        
        travelPackage.setCurrentParticipants(newCount);
        travelPackageRepository.save(travelPackage);
    }
}
