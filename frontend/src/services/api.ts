import { 
  User, 
  LoginRequest, 
  RegisterRequest, 
  Destination, 
  TravelPackage, 
  Booking, 
  CreateBookingRequest,
  ApiResponse 
} from '../types';

const API_BASE_URL = 'http://localhost:8080/api';

class ApiService {
  private async request<T>(
    endpoint: string, 
    options: RequestInit = {}
  ): Promise<T> {
    const url = `${API_BASE_URL}${endpoint}`;
    
    const config: RequestInit = {
      headers: {
        'Content-Type': 'application/json',
        ...options.headers,
      },
      ...options,
    };

    try {
      const response = await fetch(url, config);
      
      if (!response.ok) {
        const errorData = await response.json().catch(() => ({}));
        throw new Error(errorData.error || `HTTP error! status: ${response.status}`);
      }

      // Handle empty responses (like DELETE operations)
      if (response.status === 204 || response.headers.get('content-length') === '0') {
        return {} as T;
      }

      return await response.json();
    } catch (error) {
      console.error('API request failed:', error);
      throw error;
    }
  }

  // Authentication API
  async register(userData: RegisterRequest): Promise<ApiResponse<User>> {
    return this.request<ApiResponse<User>>('/auth/register', {
      method: 'POST',
      body: JSON.stringify(userData),
    });
  }

  async login(credentials: LoginRequest): Promise<ApiResponse<User>> {
    return this.request<ApiResponse<User>>('/auth/login', {
      method: 'POST',
      body: JSON.stringify(credentials),
    });
  }

  async getUserProfile(userId: number): Promise<User> {
    return this.request<User>(`/auth/profile/${userId}`);
  }

  async updateUserProfile(userId: number, userData: Partial<User>): Promise<User> {
    return this.request<User>(`/auth/profile/${userId}`, {
      method: 'PUT',
      body: JSON.stringify(userData),
    });
  }

  // Destinations API
  async getDestinations(): Promise<Destination[]> {
    return this.request<Destination[]>('/destinations');
  }

  async getFeaturedDestinations(): Promise<Destination[]> {
    return this.request<Destination[]>('/destinations/featured');
  }

  async getDestinationById(id: number): Promise<Destination> {
    return this.request<Destination>(`/destinations/${id}`);
  }

  async getDestinationsByCountry(country: string): Promise<Destination[]> {
    return this.request<Destination[]>(`/destinations/country/${encodeURIComponent(country)}`);
  }

  async getDestinationsByCity(city: string): Promise<Destination[]> {
    return this.request<Destination[]>(`/destinations/city/${encodeURIComponent(city)}`);
  }

  async searchDestinations(query: string): Promise<Destination[]> {
    return this.request<Destination[]>(`/destinations/search?q=${encodeURIComponent(query)}`);
  }

  async getDestinationsByPriceRange(minPrice: number, maxPrice: number): Promise<Destination[]> {
    return this.request<Destination[]>(`/destinations/price-range?minPrice=${minPrice}&maxPrice=${maxPrice}`);
  }

  // Travel Packages API
  async getTravelPackages(): Promise<TravelPackage[]> {
    return this.request<TravelPackage[]>('/packages');
  }

  async getFeaturedPackages(): Promise<TravelPackage[]> {
    return this.request<TravelPackage[]>('/packages/featured');
  }

  async getPackageById(id: number): Promise<TravelPackage> {
    return this.request<TravelPackage>(`/packages/${id}`);
  }

  async getPackagesByDestination(destinationId: number): Promise<TravelPackage[]> {
    return this.request<TravelPackage[]>(`/packages/destination/${destinationId}`);
  }

  async getPackagesByType(packageType: string): Promise<TravelPackage[]> {
    return this.request<TravelPackage[]>(`/packages/type/${packageType}`);
  }

  async getAvailablePackages(startDate: string): Promise<TravelPackage[]> {
    return this.request<TravelPackage[]>(`/packages/available?startDate=${startDate}`);
  }

  async getAvailablePackagesWithSpace(): Promise<TravelPackage[]> {
    return this.request<TravelPackage[]>('/packages/available-space');
  }

  async searchPackages(query: string): Promise<TravelPackage[]> {
    return this.request<TravelPackage[]>(`/packages/search?q=${encodeURIComponent(query)}`);
  }

  async getPackagesByPriceRange(minPrice: number, maxPrice: number): Promise<TravelPackage[]> {
    return this.request<TravelPackage[]>(`/packages/price-range?minPrice=${minPrice}&maxPrice=${maxPrice}`);
  }

  // Bookings API
  async getBookings(): Promise<Booking[]> {
    return this.request<Booking[]>('/bookings');
  }

  async getBookingsByUser(userId: number): Promise<Booking[]> {
    return this.request<Booking[]>(`/bookings/user/${userId}`);
  }

  async getBookingById(id: number): Promise<Booking> {
    return this.request<Booking>(`/bookings/${id}`);
  }

  async getBookingByReference(reference: string): Promise<Booking> {
    return this.request<Booking>(`/bookings/reference/${reference}`);
  }

  async getBookingsByStatus(status: string): Promise<Booking[]> {
    return this.request<Booking[]>(`/bookings/status/${status}`);
  }

  async getBookingsByPaymentStatus(status: string): Promise<Booking[]> {
    return this.request<Booking[]>(`/bookings/payment-status/${status}`);
  }

  async getConfirmedBookingsByUser(userId: number): Promise<Booking[]> {
    return this.request<Booking[]>(`/bookings/user/${userId}/confirmed`);
  }

  async createBooking(bookingData: CreateBookingRequest): Promise<Booking> {
    return this.request<Booking>('/bookings', {
      method: 'POST',
      body: JSON.stringify(bookingData),
    });
  }

  async updateBooking(id: number, bookingData: Partial<Booking>): Promise<Booking> {
    return this.request<Booking>(`/bookings/${id}`, {
      method: 'PUT',
      body: JSON.stringify(bookingData),
    });
  }

  async updateBookingStatus(id: number, status: string): Promise<Booking> {
    return this.request<Booking>(`/bookings/${id}/status`, {
      method: 'PATCH',
      body: JSON.stringify(status),
    });
  }

  async updatePaymentStatus(id: number, status: string): Promise<Booking> {
    return this.request<Booking>(`/bookings/${id}/payment-status`, {
      method: 'PATCH',
      body: JSON.stringify(status),
    });
  }

  async cancelBooking(id: number): Promise<Booking> {
    return this.request<Booking>(`/bookings/${id}/cancel`, {
      method: 'PATCH',
    });
  }

  async deleteBooking(id: number): Promise<void> {
    return this.request<void>(`/bookings/${id}`, {
      method: 'DELETE',
    });
  }
}

export const apiService = new ApiService();
export default apiService;
