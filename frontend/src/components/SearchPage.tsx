import React, { useState } from 'react';
import { Destination, TravelPackage } from '../types';
import SearchAndFilter from './SearchAndFilter';
import SearchResults from './SearchResults';
import './SearchPage.css';

const SearchPage: React.FC = () => {
  const [destinations, setDestinations] = useState<Destination[]>([]);
  const [packages, setPackages] = useState<TravelPackage[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [selectedPackage, setSelectedPackage] = useState<TravelPackage | null>(null);
  const [isBookingModalOpen, setIsBookingModalOpen] = useState(false);

  const handleDestinationsFound = (destinations: Destination[]) => {
    setDestinations(destinations);
  };

  const handlePackagesFound = (packages: TravelPackage[]) => {
    setPackages(packages);
  };

  const handleLoading = (loading: boolean) => {
    setLoading(loading);
  };

  const handleError = (error: string | null) => {
    setError(error);
  };

  const handleBookPackage = (package_: TravelPackage) => {
    setSelectedPackage(package_);
    setIsBookingModalOpen(true);
  };

  const handleCloseBookingModal = () => {
    setIsBookingModalOpen(false);
    setSelectedPackage(null);
  };

  const handleBookingSuccess = () => {
    // Refresh packages data to update availability
    setPackages(prevPackages => 
      prevPackages.map(pkg => 
        pkg.id === selectedPackage?.id 
          ? { ...pkg, currentParticipants: pkg.currentParticipants + 1 }
          : pkg
      )
    );
    setIsBookingModalOpen(false);
    setSelectedPackage(null);
  };

  return (
    <div className="search-page">
      <div className="container">
        <div className="search-page-header">
          <h1>Find Your Perfect Trip</h1>
          <p>Search through our amazing destinations and travel packages</p>
        </div>

        <SearchAndFilter
          onDestinationsFound={handleDestinationsFound}
          onPackagesFound={handlePackagesFound}
          onLoading={handleLoading}
          onError={handleError}
        />

        <SearchResults
          destinations={destinations}
          packages={packages}
          loading={loading}
          error={error}
          onBookPackage={handleBookPackage}
          selectedPackage={selectedPackage}
          isBookingModalOpen={isBookingModalOpen}
          onCloseBookingModal={handleCloseBookingModal}
          onBookingSuccess={handleBookingSuccess}
        />
      </div>
    </div>
  );
};

export default SearchPage;
