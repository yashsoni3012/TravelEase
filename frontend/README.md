# TravelEase - Travel Management System

A comprehensive travel management system built with React.js frontend and Spring Boot backend, providing a complete solution for travel booking and destination management.

## 🌟 Features

### Frontend (React.js + Vite)
- **Modern UI/UX** with responsive design
- **Real-time data fetching** from backend APIs
- **Advanced search and filtering** for destinations and packages
- **Interactive booking system** with modal-based interface
- **User authentication** with login/register functionality
- **Dynamic content** with loading states and error handling
- **Mobile-responsive** design for all devices

### Backend (Spring Boot)
- **RESTful API** with comprehensive endpoints
- **JPA/Hibernate** for database operations
- **Spring Security** for authentication
- **MySQL database** integration
- **CORS configuration** for frontend integration
- **Sample data** initialization
- **Complete CRUD operations** for all entities

## 🏗️ Project Structure

```
TravelEase/
├── travel-management-system/          # React Frontend
│   ├── src/
│   │   ├── components/               # React Components
│   │   │   ├── HomePage.tsx         # Main homepage
│   │   │   ├── Navbar.tsx           # Navigation bar
│   │   │   ├── SearchPage.tsx       # Search functionality
│   │   │   ├── BookingModal.tsx     # Booking interface
│   │   │   └── AuthModal.tsx        # Authentication
│   │   ├── services/                # API Services
│   │   │   └── api.ts              # Backend API integration
│   │   ├── types/                   # TypeScript interfaces
│   │   │   └── index.ts            # Type definitions
│   │   ├── context/                 # React Context
│   │   │   └── AuthContext.tsx     # Authentication context
│   │   └── App.tsx                  # Main App component
│   ├── package.json
│   └── README.md
└── travel-management-backend/         # Spring Boot Backend
    ├── src/main/java/com/travelmanagement/
    │   ├── entity/                  # JPA Entities
    │   ├── repository/              # Data Access Layer
    │   ├── service/                 # Business Logic
    │   ├── controller/              # REST Controllers
    │   └── config/                  # Configuration
    ├── pom.xml
    └── README.md
```

## 🚀 Getting Started

### Prerequisites
- **Node.js** (v16 or higher)
- **Java** (v17 or higher)
- **Maven** (v3.6 or higher)
- **MySQL** (v8.0 or higher)

### Backend Setup

1. **Navigate to backend directory:**
   ```bash
   cd travel-management-backend
   ```

2. **Configure database:**
   - Create MySQL database: `travel_management`
   - Update `src/main/resources/application.properties` with your database credentials

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```
   - Backend will be available at: `http://localhost:8080`

### Frontend Setup

1. **Navigate to frontend directory:**
   ```bash
   cd travel-management-system
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Start development server:**
   ```bash
   npm run dev
   ```
   - Frontend will be available at: `http://localhost:5173`

## 📊 Database Schema

### Entities
- **User**: User management with roles (USER/ADMIN)
- **Destination**: Travel destinations with detailed information
- **TravelPackage**: Travel packages linked to destinations
- **Booking**: Complete booking system with status tracking

### Sample Data
The application automatically initializes with:
- Admin user (username: `admin`, password: `admin123`)
- Regular user (username: `john_doe`, password: `password123`)
- Sample destinations (Paris, Tokyo, Bali, New York, London)
- Sample travel packages with different types and pricing

## 🔌 API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `GET /api/auth/profile/{id}` - Get user profile
- `PUT /api/auth/profile/{id}` - Update user profile

### Destinations
- `GET /api/destinations` - Get all active destinations
- `GET /api/destinations/featured` - Get featured destinations
- `GET /api/destinations/search?q={term}` - Search destinations
- `GET /api/destinations/price-range?minPrice={min}&maxPrice={max}` - Filter by price

### Travel Packages
- `GET /api/packages` - Get all active packages
- `GET /api/packages/featured` - Get featured packages
- `GET /api/packages/destination/{destinationId}` - Get packages by destination
- `GET /api/packages/available-space` - Get packages with available space

### Bookings
- `POST /api/bookings` - Create booking
- `GET /api/bookings/user/{userId}` - Get user's bookings
- `PATCH /api/bookings/{id}/status` - Update booking status
- `PATCH /api/bookings/{id}/cancel` - Cancel booking

## 🎨 Frontend Features

### Homepage
- **Hero section** with call-to-action
- **Featured destinations** with real backend data
- **Travel packages** with booking functionality
- **Features showcase** highlighting system benefits

### Search & Filter
- **Advanced search** for destinations and packages
- **Price range filtering**
- **Package type filtering** (Budget, Standard, Luxury, Premium)
- **Real-time results** with backend integration

### Booking System
- **Modal-based booking** interface
- **Participant selection** with availability checking
- **Price calculation** based on participants
- **Special requests** handling
- **Booking confirmation** with reference number

### Authentication
- **Login/Register modals** with form validation
- **User session management**
- **Protected features** for authenticated users
- **User profile** display in navigation

## 🛠️ Technologies Used

### Frontend
- **React.js** - UI framework
- **TypeScript** - Type safety
- **Vite** - Build tool and dev server
- **React Router** - Client-side routing
- **CSS3** - Styling with modern features
- **Fetch API** - HTTP requests

### Backend
- **Spring Boot** - Application framework
- **Spring Data JPA** - Data persistence
- **Spring Security** - Authentication & authorization
- **MySQL** - Database
- **Maven** - Dependency management
- **Lombok** - Code generation

## 📱 Responsive Design

The application is fully responsive and optimized for:
- **Desktop** (1200px+)
- **Tablet** (768px - 1199px)
- **Mobile** (320px - 767px)

## 🔒 Security Features

- **Password encryption** using BCrypt
- **CORS configuration** for secure cross-origin requests
- **Input validation** on both frontend and backend
- **SQL injection protection** through JPA
- **XSS protection** with proper data sanitization

## 🚀 Deployment

### Frontend Deployment
```bash
npm run build
# Deploy the 'dist' folder to your hosting service
```

### Backend Deployment
```bash
mvn clean package
# Deploy the generated JAR file to your server
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Yash Soni**
- GitHub: [@yashsoni3012](https://github.com/yashsoni3012)

## 🙏 Acknowledgments

- Spring Boot community for excellent documentation
- React.js team for the amazing framework
- All open-source contributors who made this project possible

---

**Happy Traveling! 🌍✈️**
