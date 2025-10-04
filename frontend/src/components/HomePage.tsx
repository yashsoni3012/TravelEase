import React, { useState, useEffect } from 'react';
import { Destination, TravelPackage } from '../types';
import { apiService } from '../services/api';
import BookingModal from './BookingModal';
import './HomePage.css';

const HomePage: React.FC = () => {
  const [destinations, setDestinations] = useState<Destination[]>([]);
  const [packages, setPackages] = useState<TravelPackage[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedPackage, setSelectedPackage] = useState<TravelPackage | null>(null);
  const [isBookingModalOpen, setIsBookingModalOpen] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        const [destinationsData, packagesData] = await Promise.all([
          apiService.getFeaturedDestinations(),
          apiService.getFeaturedPackages()
        ]);
        setDestinations(destinationsData);
        setPackages(packagesData);
      } catch (err) {
        setError('Failed to load data. Please try again later.');
        console.error('Error fetching data:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const handleBookPackage = (package_: TravelPackage) => {
    setSelectedPackage(package_);
    setIsBookingModalOpen(true);
  };

  const handleBookingSuccess = () => {
    // Refresh packages data to update availability
    const refreshData = async () => {
      try {
        const [destinationsData, packagesData] = await Promise.all([
          apiService.getFeaturedDestinations(),
          apiService.getFeaturedPackages()
        ]);
        setDestinations(destinationsData);
        setPackages(packagesData);
      } catch (err) {
        console.error('Error refreshing data:', err);
      }
    };
    refreshData();
  };

  if (loading) {
    return (
      <div className="homepage">
        <div className="loading-container">
          <div className="loading-spinner"></div>
          <p>Loading amazing destinations...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="homepage">
        <div className="error-container">
          <h2>Oops! Something went wrong</h2>
          <p>{error}</p>
          <button onClick={() => window.location.reload()} className="btn-primary">
            Try Again
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="homepage">
      {/* Hero Section */}
      <section className="hero">
        <div className="hero-content">
          <h1 className="hero-title">
            Discover Amazing
            <span className="highlight"> Destinations</span>
          </h1>
          <p className="hero-subtitle">
            Plan your perfect getaway with our comprehensive travel management system. 
            From booking flights to finding the best hotels, we've got you covered.
          </p>
          <div className="hero-buttons">
            <button className="btn-primary">Start Planning</button>
            <button className="btn-secondary">View Destinations</button>
          </div>
        </div>
        <div className="hero-image">
          <div className="floating-card">
            <div className="card-icon">✈️</div>
            <div className="card-text">
              <h4>Book Flights</h4>
              <p>Best prices guaranteed</p>
            </div>
          </div>
          <div className="floating-card">
            <div className="card-icon">🏨</div>
            <div className="card-text">
              <h4>Find Hotels</h4>
              <p>Luxury & budget options</p>
            </div>
          </div>
          <div className="floating-card">
            <div className="card-icon">🗺️</div>
            <div className="card-text">
              <h4>Plan Itinerary</h4>
              <p>Personalized experiences</p>
            </div>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="features">
        <div className="container">
          <h2 className="section-title">Why Choose TravelEase?</h2>
          <div className="features-grid">
            <div className="feature-card">
              <div className="feature-icon">🌍</div>
              <h3>Global Coverage</h3>
              <p>Access to destinations worldwide with local expertise and insider knowledge.</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">💰</div>
              <h3>Best Prices</h3>
              <p>Competitive pricing with price match guarantee and exclusive deals.</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">🛡️</div>
              <h3>24/7 Support</h3>
              <p>Round-the-clock customer service to assist you throughout your journey.</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">📱</div>
              <h3>Mobile App</h3>
              <p>Manage your bookings on-the-go with our user-friendly mobile application.</p>
            </div>
          </div>
        </div>
      </section>

      {/* Popular Destinations */}
      <section className="destinations">
        <div className="container">
          <h2 className="section-title">Popular Destinations</h2>
          <div className="destinations-grid">
            {destinations.map((destination) => (
              <div key={destination.id} className="destination-card">
                <div 
                  className="destination-image"
                  style={{
                    backgroundImage: destination.imageUrl 
                      ? `url(${destination.imageUrl})` 
                      : `linear-gradient(rgba(0,0,0,0.3), rgba(0,0,0,0.3)), url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 400 200"><rect fill="%23E8E8E8" width="400" height="200"/><text x="200" y="100" text-anchor="middle" fill="%23999" font-size="16">${destination.name} Image</text></svg>')`
                  }}
                ></div>
                <div className="destination-content">
                  <h3>{destination.name}, {destination.country}</h3>
                  <p>{destination.description.substring(0, 100)}...</p>
                  <div className="destination-price">
                    From ${destination.price} {destination.currency}
                  </div>
                  <div className="destination-details">
                    <small>Best time: {destination.bestTimeToVisit}</small>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Featured Packages */}
      <section className="packages">
        <div className="container">
          <h2 className="section-title">Featured Travel Packages</h2>
          <div className="packages-grid">
            {packages.map((package_) => (
              <div key={package_.id} className="package-card">
                <div className="package-header">
                  <h3>{package_.name}</h3>
                  <div className="package-type">{package_.packageType}</div>
                </div>
                <div className="package-destination">
                  <strong>{package_.destination.name}, {package_.destination.country}</strong>
                </div>
                <div className="package-dates">
                  {new Date(package_.startDate).toLocaleDateString()} - {new Date(package_.endDate).toLocaleDateString()}
                </div>
                <div className="package-description">
                  {package_.description.substring(0, 150)}...
                </div>
                <div className="package-details">
                  <div className="package-price">
                    ${package_.price} {package_.currency} per person
                  </div>
                  <div className="package-participants">
                    {package_.currentParticipants}/{package_.maxParticipants} participants
                  </div>
                </div>
                <div className="package-includes">
                  <strong>Includes:</strong> {package_.includes}
                </div>
                <button 
                  className="btn-package"
                  onClick={() => handleBookPackage(package_)}
                  disabled={package_.currentParticipants >= package_.maxParticipants}
                >
                  {package_.currentParticipants >= package_.maxParticipants ? 'Fully Booked' : 'Book Now'}
                </button>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="cta">
        <div className="container">
          <div className="cta-content">
            <h2>Ready to Start Your Journey?</h2>
            <p>Join thousands of satisfied travelers who trust TravelEase for their adventures.</p>
            <button className="btn-cta">Get Started Today</button>
          </div>
        </div>
      </section>

      {/* Booking Modal */}
      <BookingModal
        isOpen={isBookingModalOpen}
        onClose={() => setIsBookingModalOpen(false)}
        travelPackage={selectedPackage}
        onBookingSuccess={handleBookingSuccess}
      />
    </div>
  );
};

export default HomePage;
