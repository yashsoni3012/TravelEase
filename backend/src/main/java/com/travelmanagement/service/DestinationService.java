package com.travelmanagement.service;

import com.travelmanagement.entity.Destination;
import com.travelmanagement.repository.DestinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DestinationService {
    
    private final DestinationRepository destinationRepository;
    
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }
    
    public List<Destination> getActiveDestinations() {
        return destinationRepository.findByIsActiveTrue();
    }
    
    public List<Destination> getFeaturedDestinations() {
        return destinationRepository.findByIsFeaturedTrueAndIsActiveTrue();
    }
    
    public Optional<Destination> getDestinationById(Long id) {
        return destinationRepository.findById(id);
    }
    
    public List<Destination> getDestinationsByCountry(String country) {
        return destinationRepository.findByCountry(country);
    }
    
    public List<Destination> getDestinationsByCity(String city) {
        return destinationRepository.findByCity(city);
    }
    
    public List<Destination> searchDestinations(String searchTerm) {
        return destinationRepository.searchDestinations(searchTerm);
    }
    
    public List<Destination> getDestinationsByPriceRange(Double minPrice, Double maxPrice) {
        return destinationRepository.findByPriceRange(minPrice, maxPrice);
    }
    
    public Destination createDestination(Destination destination) {
        return destinationRepository.save(destination);
    }
    
    public Destination updateDestination(Long id, Destination destinationDetails) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found with id: " + id));
        
        destination.setName(destinationDetails.getName());
        destination.setCountry(destinationDetails.getCountry());
        destination.setCity(destinationDetails.getCity());
        destination.setDescription(destinationDetails.getDescription());
        destination.setImageUrl(destinationDetails.getImageUrl());
        destination.setPrice(destinationDetails.getPrice());
        destination.setCurrency(destinationDetails.getCurrency());
        destination.setBestTimeToVisit(destinationDetails.getBestTimeToVisit());
        destination.setClimate(destinationDetails.getClimate());
        destination.setPopularAttractions(destinationDetails.getPopularAttractions());
        destination.setIsFeatured(destinationDetails.getIsFeatured());
        destination.setIsActive(destinationDetails.getIsActive());
        
        return destinationRepository.save(destination);
    }
    
    public void deleteDestination(Long id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found with id: " + id));
        destinationRepository.delete(destination);
    }
    
    public void toggleDestinationStatus(Long id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found with id: " + id));
        destination.setIsActive(!destination.getIsActive());
        destinationRepository.save(destination);
    }
}
