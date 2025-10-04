import React, { useState } from 'react';
import { TravelPackage, CreateBookingRequest } from '../types';
import { apiService } from '../services/api';
import './BookingModal.css';

interface BookingModalProps {
  isOpen: boolean;
  onClose: () => void;
  travelPackage: TravelPackage | null;
  onBookingSuccess: () => void;
}

const BookingModal: React.FC<BookingModalProps> = ({
  isOpen,
  onClose,
  travelPackage,
  onBookingSuccess
}) => {
  const [formData, setFormData] = useState({
    numberOfParticipants: 1,
    specialRequests: ''
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  if (!isOpen || !travelPackage) return null;

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      // For demo purposes, using a hardcoded user ID
      // In a real app, this would come from authentication context
      const bookingData: CreateBookingRequest = {
        userId: 2, // This should come from user context
        travelPackageId: travelPackage.id,
        numberOfParticipants: formData.numberOfParticipants,
        specialRequests: formData.specialRequests || undefined
      };

      await apiService.createBooking(bookingData);
      onBookingSuccess();
      onClose();
      
      // Reset form
      setFormData({
        numberOfParticipants: 1,
        specialRequests: ''
      });
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to create booking');
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: name === 'numberOfParticipants' ? parseInt(value) || 1 : value
    }));
  };

  const maxParticipants = travelPackage.maxParticipants - travelPackage.currentParticipants;
  const totalPrice = travelPackage.price * formData.numberOfParticipants;

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <h2>Book Your Trip</h2>
          <button className="close-button" onClick={onClose}>×</button>
        </div>

        <div className="modal-body">
          <div className="package-summary">
            <h3>{travelPackage.name}</h3>
            <p><strong>Destination:</strong> {travelPackage.destination.name}, {travelPackage.destination.country}</p>
            <p><strong>Dates:</strong> {new Date(travelPackage.startDate).toLocaleDateString()} - {new Date(travelPackage.endDate).toLocaleDateString()}</p>
            <p><strong>Package Type:</strong> {travelPackage.packageType}</p>
            <p><strong>Available Spots:</strong> {maxParticipants}</p>
          </div>

          <form onSubmit={handleSubmit} className="booking-form">
            <div className="form-group">
              <label htmlFor="numberOfParticipants">Number of Participants</label>
              <input
                type="number"
                id="numberOfParticipants"
                name="numberOfParticipants"
                value={formData.numberOfParticipants}
                onChange={handleInputChange}
                min="1"
                max={maxParticipants}
                required
              />
              <small>Maximum {maxParticipants} participants available</small>
            </div>

            <div className="form-group">
              <label htmlFor="specialRequests">Special Requests (Optional)</label>
              <textarea
                id="specialRequests"
                name="specialRequests"
                value={formData.specialRequests}
                onChange={handleInputChange}
                rows={3}
                placeholder="Any special dietary requirements, accessibility needs, or other requests..."
              />
            </div>

            <div className="price-summary">
              <div className="price-breakdown">
                <div className="price-item">
                  <span>Price per person:</span>
                  <span>${travelPackage.price} {travelPackage.currency}</span>
                </div>
                <div className="price-item">
                  <span>Number of participants:</span>
                  <span>{formData.numberOfParticipants}</span>
                </div>
                <div className="price-total">
                  <span><strong>Total Price:</strong></span>
                  <span><strong>${totalPrice} {travelPackage.currency}</strong></span>
                </div>
              </div>
            </div>

            {error && (
              <div className="error-message">
                {error}
              </div>
            )}

            <div className="modal-actions">
              <button type="button" onClick={onClose} className="btn-secondary">
                Cancel
              </button>
              <button type="submit" disabled={loading} className="btn-primary">
                {loading ? 'Processing...' : 'Confirm Booking'}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default BookingModal;
