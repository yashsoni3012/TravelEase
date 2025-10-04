import React from 'react';
import { Destination, TravelPackage } from '../types';
import BookingModal from './BookingModal';
import './SearchResults.css';

interface SearchResultsProps {
  destinations: Destination[];
  packages: TravelPackage[];
  loading: boolean;
  error: string | null;
  onBookPackage: (package_: TravelPackage) => void;
  selectedPackage: TravelPackage | null;
  isBookingModalOpen: boolean;
  onCloseBookingModal: () => void;
  onBookingSuccess: () => void;
}

const SearchResults: React.FC<SearchResultsProps> = ({
  destinations,
  packages,
  loading,
  error,
  onBookPackage,
  selectedPackage,
  isBookingModalOpen,
  onCloseBookingModal,
  onBookingSuccess
}) => {
  if (loading) {
    return (
      <div className="search-results">
        <div className="loading-container">
          <div className="loading-spinner"></div>
          <p>Searching for amazing destinations...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="search-results">
        <div className="error-container">
          <h3>Search Error</h3>
          <p>{error}</p>
        </div>
      </div>
    );
  }

  if (destinations.length === 0 && packages.length === 0) {
    return (
      <div className="search-results">
        <div className="no-results">
          <h3>No Results Found</h3>
          <p>Try adjusting your search criteria or browse our featured destinations and packages.</p>
        </div>
      </div>
    );
  }

  return (
    <div className="search-results">
      {destinations.length > 0 && (
        <section className="results-section">
          <h2 className="results-title">Destinations ({destinations.length})</h2>
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
        </section>
      )}

      {packages.length > 0 && (
        <section className="results-section">
          <h2 className="results-title">Travel Packages ({packages.length})</h2>
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
                  onClick={() => onBookPackage(package_)}
                  disabled={package_.currentParticipants >= package_.maxParticipants}
                >
                  {package_.currentParticipants >= package_.maxParticipants ? 'Fully Booked' : 'Book Now'}
                </button>
              </div>
            ))}
          </div>
        </section>
      )}

      {/* Booking Modal */}
      <BookingModal
        isOpen={isBookingModalOpen}
        onClose={onCloseBookingModal}
        travelPackage={selectedPackage}
        onBookingSuccess={onBookingSuccess}
      />
    </div>
  );
};

export default SearchResults;
