// User types
export interface User {
  id: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  phoneNumber?: string;
  role: 'USER' | 'ADMIN';
  createdAt: string;
  updatedAt: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  phoneNumber?: string;
}

// Destination types
export interface Destination {
  id: number;
  name: string;
  country: string;
  city: string;
  description: string;
  imageUrl?: string;
  price: number;
  currency: string;
  bestTimeToVisit?: string;
  climate?: string;
  popularAttractions?: string;
  isFeatured: boolean;
  isActive: boolean;
  createdAt: string;
  updatedAt: string;
}

// Travel Package types
export interface TravelPackage {
  id: number;
  name: string;
  description: string;
  destination: Destination;
  startDate: string;
  endDate: string;
  price: number;
  currency: string;
  maxParticipants: number;
  currentParticipants: number;
  packageType: 'BUDGET' | 'STANDARD' | 'LUXURY' | 'PREMIUM';
  includes?: string;
  excludes?: string;
  itinerary?: string;
  isFeatured: boolean;
  isActive: boolean;
  createdAt: string;
  updatedAt: string;
}

// Booking types
export interface Booking {
  id: number;
  user: User;
  travelPackage: TravelPackage;
  numberOfParticipants: number;
  totalPrice: number;
  currency: string;
  bookingStatus: 'PENDING' | 'CONFIRMED' | 'CANCELLED' | 'COMPLETED';
  paymentStatus: 'PENDING' | 'PAID' | 'REFUNDED' | 'FAILED';
  specialRequests?: string;
  bookingReference: string;
  bookingDate: string;
  createdAt: string;
  updatedAt: string;
}

export interface CreateBookingRequest {
  userId: number;
  travelPackageId: number;
  numberOfParticipants: number;
  specialRequests?: string;
}

// API Response types
export interface ApiResponse<T> {
  data?: T;
  message?: string;
  error?: string;
}

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}
