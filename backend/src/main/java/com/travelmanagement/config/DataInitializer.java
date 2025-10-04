package com.travelmanagement.config;

import com.travelmanagement.entity.Destination;
import com.travelmanagement.entity.TravelPackage;
import com.travelmanagement.entity.User;
import com.travelmanagement.repository.DestinationRepository;
import com.travelmanagement.repository.TravelPackageRepository;
import com.travelmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DestinationRepository destinationRepository;
    private final TravelPackageRepository travelPackageRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialize sample data only if database is empty
        if (userRepository.count() == 0) {
            initializeUsers();
        }
        
        if (destinationRepository.count() == 0) {
            initializeDestinations();
        }
        
        if (travelPackageRepository.count() == 0) {
            initializeTravelPackages();
        }
    }

    private void initializeUsers() {
        // Create admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@travelease.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setRole(User.Role.ADMIN);
        userRepository.save(admin);

        // Create regular user
        User user = new User();
        user.setUsername("john_doe");
        user.setEmail("john@example.com");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPhoneNumber("+1234567890");
        user.setRole(User.Role.USER);
        userRepository.save(user);
    }

    private void initializeDestinations() {
        // Paris, France
        Destination paris = new Destination();
        paris.setName("Paris");
        paris.setCountry("France");
        paris.setCity("Paris");
        paris.setDescription("The City of Light, famous for its art, fashion, and cuisine. Home to the Eiffel Tower, Louvre Museum, and Notre-Dame Cathedral.");
        paris.setPrice(899.0);
        paris.setCurrency("USD");
        paris.setBestTimeToVisit("April to June, September to November");
        paris.setClimate("Temperate");
        paris.setPopularAttractions("Eiffel Tower, Louvre Museum, Notre-Dame Cathedral, Champs-Élysées, Arc de Triomphe");
        paris.setIsFeatured(true);
        paris.setIsActive(true);
        destinationRepository.save(paris);

        // Tokyo, Japan
        Destination tokyo = new Destination();
        tokyo.setName("Tokyo");
        tokyo.setCountry("Japan");
        tokyo.setCity("Tokyo");
        tokyo.setDescription("A vibrant metropolis blending traditional culture with cutting-edge technology. Experience ancient temples alongside futuristic skyscrapers.");
        tokyo.setPrice(1299.0);
        tokyo.setCurrency("USD");
        tokyo.setBestTimeToVisit("March to May, September to November");
        tokyo.setClimate("Humid subtropical");
        tokyo.setPopularAttractions("Tokyo Skytree, Senso-ji Temple, Shibuya Crossing, Tsukiji Fish Market, Meiji Shrine");
        tokyo.setIsFeatured(true);
        tokyo.setIsActive(true);
        destinationRepository.save(tokyo);

        // Bali, Indonesia
        Destination bali = new Destination();
        bali.setName("Bali");
        bali.setCountry("Indonesia");
        bali.setCity("Denpasar");
        bali.setDescription("A tropical paradise known for its stunning beaches, lush rice terraces, and rich cultural heritage.");
        bali.setPrice(699.0);
        bali.setCurrency("USD");
        bali.setBestTimeToVisit("April to October");
        bali.setClimate("Tropical");
        bali.setPopularAttractions("Ubud Rice Terraces, Tanah Lot Temple, Mount Batur, Seminyak Beach, Uluwatu Temple");
        bali.setIsFeatured(true);
        bali.setIsActive(true);
        destinationRepository.save(bali);

        // New York, USA
        Destination newYork = new Destination();
        newYork.setName("New York City");
        newYork.setCountry("USA");
        newYork.setCity("New York");
        newYork.setDescription("The Big Apple, a bustling metropolis that never sleeps. Experience world-class dining, shopping, and entertainment.");
        newYork.setPrice(1199.0);
        newYork.setCurrency("USD");
        newYork.setBestTimeToVisit("April to June, September to November");
        newYork.setClimate("Humid subtropical");
        newYork.setPopularAttractions("Statue of Liberty, Central Park, Times Square, Empire State Building, Broadway");
        newYork.setIsFeatured(true);
        newYork.setIsActive(true);
        destinationRepository.save(newYork);

        // London, UK
        Destination london = new Destination();
        london.setName("London");
        london.setCountry("United Kingdom");
        london.setCity("London");
        london.setDescription("A historic city with royal palaces, world-class museums, and a vibrant cultural scene.");
        london.setPrice(1099.0);
        london.setCurrency("USD");
        london.setBestTimeToVisit("May to September");
        london.setClimate("Temperate oceanic");
        london.setPopularAttractions("Big Ben, Tower of London, Buckingham Palace, British Museum, London Eye");
        london.setIsFeatured(false);
        london.setIsActive(true);
        destinationRepository.save(london);
    }

    private void initializeTravelPackages() {
        // Get destinations
        Destination paris = destinationRepository.findByName("Paris").orElse(null);
        Destination tokyo = destinationRepository.findByName("Tokyo").orElse(null);
        Destination bali = destinationRepository.findByName("Bali").orElse(null);
        Destination newYork = destinationRepository.findByName("New York City").orElse(null);

        if (paris != null) {
            // Paris Package 1
            TravelPackage parisPackage1 = new TravelPackage();
            parisPackage1.setName("Paris Romance Package");
            parisPackage1.setDescription("A romantic 5-day getaway to the City of Light, perfect for couples.");
            parisPackage1.setDestination(paris);
            parisPackage1.setStartDate(LocalDate.now().plusDays(30));
            parisPackage1.setEndDate(LocalDate.now().plusDays(35));
            parisPackage1.setPrice(1299.0);
            parisPackage1.setCurrency("USD");
            parisPackage1.setMaxParticipants(20);
            parisPackage1.setPackageType(TravelPackage.PackageType.LUXURY);
            parisPackage1.setIncludes("Hotel accommodation, breakfast, city tour, Seine river cruise, museum passes");
            parisPackage1.setExcludes("Airfare, meals not specified, personal expenses");
            parisPackage1.setItinerary("Day 1: Arrival and city orientation, Day 2: Louvre Museum and Notre-Dame, Day 3: Eiffel Tower and Seine cruise, Day 4: Montmartre and Sacré-Cœur, Day 5: Departure");
            parisPackage1.setIsFeatured(true);
            parisPackage1.setIsActive(true);
            travelPackageRepository.save(parisPackage1);

            // Paris Package 2
            TravelPackage parisPackage2 = new TravelPackage();
            parisPackage2.setName("Paris Budget Explorer");
            parisPackage2.setDescription("An affordable 3-day exploration of Paris for budget-conscious travelers.");
            parisPackage2.setDestination(paris);
            parisPackage2.setStartDate(LocalDate.now().plusDays(45));
            parisPackage2.setEndDate(LocalDate.now().plusDays(48));
            parisPackage2.setPrice(599.0);
            parisPackage2.setCurrency("USD");
            parisPackage2.setMaxParticipants(30);
            parisPackage2.setPackageType(TravelPackage.PackageType.BUDGET);
            parisPackage2.setIncludes("Hostel accommodation, breakfast, walking tour");
            parisPackage2.setExcludes("Airfare, meals not specified, museum entrance fees");
            parisPackage2.setItinerary("Day 1: Arrival and walking tour, Day 2: Free exploration, Day 3: Departure");
            parisPackage2.setIsFeatured(false);
            parisPackage2.setIsActive(true);
            travelPackageRepository.save(parisPackage2);
        }

        if (tokyo != null) {
            // Tokyo Package
            TravelPackage tokyoPackage = new TravelPackage();
            tokyoPackage.setName("Tokyo Cultural Experience");
            tokyoPackage.setDescription("A 7-day immersive journey through Tokyo's rich culture and modern innovation.");
            tokyoPackage.setDestination(tokyo);
            tokyoPackage.setStartDate(LocalDate.now().plusDays(60));
            tokyoPackage.setEndDate(LocalDate.now().plusDays(67));
            tokyoPackage.setPrice(1899.0);
            tokyoPackage.setCurrency("USD");
            tokyoPackage.setMaxParticipants(15);
            tokyoPackage.setPackageType(TravelPackage.PackageType.PREMIUM);
            tokyoPackage.setIncludes("Hotel accommodation, breakfast, JR Pass, cultural experiences, temple visits");
            tokyoPackage.setExcludes("Airfare, meals not specified, personal shopping");
            tokyoPackage.setItinerary("Day 1-2: Arrival and city orientation, Day 3-4: Cultural sites and temples, Day 5-6: Modern Tokyo exploration, Day 7: Departure");
            tokyoPackage.setIsFeatured(true);
            tokyoPackage.setIsActive(true);
            travelPackageRepository.save(tokyoPackage);
        }

        if (bali != null) {
            // Bali Package
            TravelPackage baliPackage = new TravelPackage();
            baliPackage.setName("Bali Beach Paradise");
            baliPackage.setDescription("A relaxing 6-day beach vacation in the tropical paradise of Bali.");
            baliPackage.setDestination(bali);
            baliPackage.setStartDate(LocalDate.now().plusDays(20));
            baliPackage.setEndDate(LocalDate.now().plusDays(26));
            baliPackage.setPrice(999.0);
            baliPackage.setCurrency("USD");
            baliPackage.setMaxParticipants(25);
            baliPackage.setPackageType(TravelPackage.PackageType.STANDARD);
            baliPackage.setIncludes("Resort accommodation, breakfast, airport transfers, temple tour");
            baliPackage.setExcludes("Airfare, meals not specified, spa treatments");
            baliPackage.setItinerary("Day 1: Arrival and beach time, Day 2: Ubud rice terraces, Day 3: Temple visits, Day 4-5: Beach relaxation, Day 6: Departure");
            baliPackage.setIsFeatured(true);
            baliPackage.setIsActive(true);
            travelPackageRepository.save(baliPackage);
        }

        if (newYork != null) {
            // New York Package
            TravelPackage nyPackage = new TravelPackage();
            nyPackage.setName("New York City Explorer");
            nyPackage.setDescription("A 4-day adventure in the city that never sleeps.");
            nyPackage.setDestination(newYork);
            nyPackage.setStartDate(LocalDate.now().plusDays(40));
            nyPackage.setEndDate(LocalDate.now().plusDays(44));
            nyPackage.setPrice(1499.0);
            nyPackage.setCurrency("USD");
            nyPackage.setMaxParticipants(20);
            nyPackage.setPackageType(TravelPackage.PackageType.STANDARD);
            nyPackage.setIncludes("Hotel accommodation, breakfast, subway pass, Broadway show ticket");
            nyPackage.setExcludes("Airfare, meals not specified, shopping");
            nyPackage.setItinerary("Day 1: Arrival and Times Square, Day 2: Statue of Liberty and Central Park, Day 3: Museums and shopping, Day 4: Departure");
            nyPackage.setIsFeatured(false);
            nyPackage.setIsActive(true);
            travelPackageRepository.save(nyPackage);
        }
    }
}
