import React, { useState } from 'react';
import { Destination, TravelPackage } from '../types';
import { apiService } from '../services/api';
import './SearchAndFilter.css';

interface SearchAndFilterProps {
  onDestinationsFound: (destinations: Destination[]) => void;
  onPackagesFound: (packages: TravelPackage[]) => void;
  onLoading: (loading: boolean) => void;
  onError: (error: string | null) => void;
}

const SearchAndFilter: React.FC<SearchAndFilterProps> = ({
  onDestinationsFound,
  onPackagesFound,
  onLoading,
  onError
}) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [minPrice, setMinPrice] = useState('');
  const [maxPrice, setMaxPrice] = useState('');
  const [packageType, setPackageType] = useState('');
  const [searchType, setSearchType] = useState<'destinations' | 'packages'>('destinations');

  const handleSearch = async () => {
    if (!searchTerm.trim() && !minPrice && !maxPrice && !packageType) {
      onError('Please enter search criteria');
      return;
    }

    onLoading(true);
    onError(null);

    try {
      if (searchType === 'destinations') {
        let destinations: Destination[] = [];

        if (searchTerm.trim()) {
          destinations = await apiService.searchDestinations(searchTerm);
        } else {
          destinations = await apiService.getDestinations();
        }

        // Apply price filter if specified
        if (minPrice || maxPrice) {
          const min = minPrice ? parseFloat(minPrice) : 0;
          const max = maxPrice ? parseFloat(maxPrice) : Infinity;
          destinations = destinations.filter(d => d.price >= min && d.price <= max);
        }

        onDestinationsFound(destinations);
      } else {
        let packages: TravelPackage[] = [];

        if (searchTerm.trim()) {
          packages = await apiService.searchPackages(searchTerm);
        } else {
          packages = await apiService.getTravelPackages();
        }

        // Apply price filter if specified
        if (minPrice || maxPrice) {
          const min = minPrice ? parseFloat(minPrice) : 0;
          const max = maxPrice ? parseFloat(maxPrice) : Infinity;
          packages = packages.filter(p => p.price >= min && p.price <= max);
        }

        // Apply package type filter if specified
        if (packageType) {
          packages = packages.filter(p => p.packageType === packageType);
        }

        onPackagesFound(packages);
      }
    } catch (err) {
      onError(err instanceof Error ? err.message : 'Search failed');
    } finally {
      onLoading(false);
    }
  };

  const handleClearFilters = () => {
    setSearchTerm('');
    setMinPrice('');
    setMaxPrice('');
    setPackageType('');
    onDestinationsFound([]);
    onPackagesFound([]);
    onError(null);
  };

  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter') {
      handleSearch();
    }
  };

  return (
    <div className="search-filter-container">
      <div className="search-filter-header">
        <h3>Search & Filter</h3>
        <div className="search-type-toggle">
          <button
            className={`toggle-btn ${searchType === 'destinations' ? 'active' : ''}`}
            onClick={() => setSearchType('destinations')}
          >
            Destinations
          </button>
          <button
            className={`toggle-btn ${searchType === 'packages' ? 'active' : ''}`}
            onClick={() => setSearchType('packages')}
          >
            Packages
          </button>
        </div>
      </div>

      <div className="search-filters">
        <div className="search-input-group">
          <input
            type="text"
            placeholder={`Search ${searchType}...`}
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            onKeyPress={handleKeyPress}
            className="search-input"
          />
        </div>

        <div className="filter-group">
          <div className="price-filters">
            <input
              type="number"
              placeholder="Min Price"
              value={minPrice}
              onChange={(e) => setMinPrice(e.target.value)}
              className="price-input"
            />
            <span className="price-separator">to</span>
            <input
              type="number"
              placeholder="Max Price"
              value={maxPrice}
              onChange={(e) => setMaxPrice(e.target.value)}
              className="price-input"
            />
          </div>

          {searchType === 'packages' && (
            <select
              value={packageType}
              onChange={(e) => setPackageType(e.target.value)}
              className="package-type-select"
            >
              <option value="">All Package Types</option>
              <option value="BUDGET">Budget</option>
              <option value="STANDARD">Standard</option>
              <option value="LUXURY">Luxury</option>
              <option value="PREMIUM">Premium</option>
            </select>
          )}
        </div>

        <div className="search-actions">
          <button onClick={handleSearch} className="btn-search">
            Search
          </button>
          <button onClick={handleClearFilters} className="btn-clear">
            Clear
          </button>
        </div>
      </div>
    </div>
  );
};

export default SearchAndFilter;
