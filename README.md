# 🎬 CineMate - Cinema Management System

[![Java](https://img.shields.io/badge/Java-24-orange.svg)](https://openjdk.java.net/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-green.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A comprehensive **Cinema Management System** built with **Java Swing** and **MySQL**. CineMate provides a complete solution for managing movie theaters, including customer bookings, seat selection, movie management, and administrative dashboard with real-time statistics.

![CineMate Logo](src/main/resources/assets/logo.png)

## ✨ Features

### 🎭 **Customer Interface**
- **Movie Catalog**: Browse available movies with detailed descriptions and posters
- **Seat Selection**: Interactive seat map with real-time availability
- **Ticket Booking**: Multi-step booking process with ticket type selection
- **Booking Confirmation**: Print-ready receipts and booking confirmations
- **Movie Details**: Comprehensive movie information including showtimes

### 👨‍💼 **Admin Panel**
- **Dashboard**: Real-time statistics (sales, tickets sold, active movies)
- **Movie Management**: Full CRUD operations for movies
- **Booking Management**: View and manage all customer bookings
- **User Management**: Track customer data and booking history
- **Statistics Tracking**: Revenue analytics and performance metrics

### 🎟️ **Booking System**
- **Multiple Ticket Types**: 2D Regular, 3D Premium, IMAX options
- **Seat Management**: Dynamic seat availability and booking
- **Price Calculation**: Automatic total calculation with different ticket prices
- **Booking Status**: PENDING, CONFIRMED, CANCELLED status tracking
- **Customer Data**: Comprehensive customer information storage

### 📊 **Dashboard Features**
- **Total Sales**: Real-time revenue tracking from confirmed bookings
- **Tickets Sold**: Count of all confirmed ticket sales
- **Active Movies**: Number of currently showing movies
- **Movie Schedule**: Complete showtime management
- **Refresh Functionality**: Live data updates

## 🛠 Technology Stack

- **Frontend**: Java Swing (Desktop GUI)
- **Backend**: Java 24
- **Database**: MySQL 8.0+
- **Connection Pooling**: HikariCP 5.0.1
- **Build Tool**: Apache Maven 3.6+
- **Logging**: SLF4J
- **Testing**: JUnit 4

## 📋 Prerequisites

Before installing CineMate, ensure you have:

- **Java Development Kit (JDK) 24** or higher
- **Apache Maven 3.6+**
- **XAMPP** (includes MySQL and phpMyAdmin)
- **Git** (for cloning the repository)

## 🚀 Installation

### 1. Clone the Repository
```bash
git clone https://github.com/riecodes/cinemate.git
cd cinemate
```

### 2. Set Up Database
Follow the detailed [Database Setup Guide](DATABASE_SETUP_GUIDE.md) for complete instructions.

**Quick Setup:**
1. Install and start XAMPP
2. Start MySQL service in XAMPP Control Panel
3. The application will automatically create the database on first run

### 3. Install Dependencies
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn exec:java -Dexec.mainClass="com.mycompany.cinemate.Cinemate"
```

**Alternative:**
```bash
mvn clean compile exec:java
```

## 📖 Usage Guide

### Customer Workflow
1. **Browse Movies**: View available movies on the main screen
2. **Select Movie**: Click on any movie poster to view details
3. **Choose Showtime**: Select preferred date and time
4. **Select Seats**: Choose seats from interactive seat map
5. **Choose Tickets**: Select ticket types (2D/3D/IMAX) and quantities
6. **Enter Details**: Provide customer information
7. **Confirm Booking**: Review and confirm reservation
8. **Print Receipt**: Get booking confirmation

### Admin Workflow
1. **Login**: Use admin credentials (admin/admin)
2. **Dashboard**: View real-time statistics and movie schedule
3. **Manage Movies**: Add, edit, or delete movies
4. **View Bookings**: Monitor all customer reservations
5. **User Management**: Track customer data and history
6. **Refresh Data**: Update statistics and information

### Default Login Credentials
- **Username**: `admin`
- **Password**: `admin`

## 📁 Project Structure

```
cinemate/
├── src/main/java/com/mycompany/cinemate/
│   ├── Cinemate.java                 # Main application entry point
│   ├── dao/                          # Data Access Objects
│   │   ├── BookingDAO.java
│   │   ├── MovieDAO.java
│   │   ├── SeatDAO.java
│   │   ├── ShowtimeDAO.java
│   │   ├── StatisticsDAO.java
│   │   └── TicketTypeDAO.java
│   ├── database/                     # Database configuration
│   │   ├── DatabaseConfig.java
│   │   ├── DatabaseReset.java
│   │   └── DatabaseSchema.java
│   ├── forms/                        # GUI Forms
│   │   ├── AdminLoginForm.java
│   │   ├── AdminPanelForm.java
│   │   ├── BookingTicketsForm.java
│   │   ├── ConfirmationForm.java
│   │   ├── DashboardForm.java
│   │   ├── MainMenuForm.java
│   │   ├── MovieDetailsForm.java
│   │   ├── MoviesForm.java
│   │   ├── ReservationForm.java
│   │   ├── SeatsForm.java
│   │   └── TicketsForm.java
│   └── models/                       # Data Models
│       ├── Booking.java
│       ├── Movie.java
│       ├── Seat.java
│       ├── Showtime.java
│       └── TicketType.java
├── src/main/resources/assets/        # Images and resources
│   ├── movie posters/               # Movie poster images
│   ├── forms/                       # Form screenshots
│   └── logo.png                     # Application logo
├── database_setup.sql               # Database initialization script
├── DATABASE_SETUP_GUIDE.md         # Detailed setup instructions
└── pom.xml                         # Maven configuration
```

## 🎬 Sample Data

The application includes sample data for immediate testing:

### Movies (10 titles):
- How To Train Your Dragon
- Megan 2.0
- Lilo & Stitch
- Ballerina
- Elio
- Pelikulaya: Young Hearts
- Only We Know
- Unconditional
- 28 Years Later
- The Ritual

### Features Included:
- **Showtimes**: Multiple daily showtimes for each movie
- **Seat Maps**: 48 seats per showing (6 rows × 8 seats)
- **Ticket Types**: 3 different pricing tiers
- **Sample Users**: Pre-registered customers

## 🖼 Screenshots

### Main Interface
- Customer movie browsing interface
- Interactive seat selection map
- Booking confirmation system

### Admin Panel
- Real-time dashboard with statistics
- Movie management interface
- Booking and user management

*Screenshots are available in `src/main/resources/assets/forms/`*

## 🔧 Configuration

### Database Settings
Modify database connection in `src/main/java/com/mycompany/cinemate/database/DatabaseConfig.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/cinemate_db";
private static final String DB_USERNAME = "root";
private static final String DB_PASSWORD = ""; // Add your password if needed
```

### Application Settings
- **Theater Capacity**: 48 seats (configurable in seat generation)
- **Ticket Prices**: Defined in ticket_types table
- **Showtime Schedules**: Configurable in showtimes table

## 🔍 Troubleshooting

### Common Issues:

**Database Connection Failed**
- Ensure XAMPP MySQL is running
- Check port 3306 availability
- Verify credentials in DatabaseConfig.java

**Application Won't Start**
- Confirm Java 24+ is installed
- Run `mvn clean install` to resolve dependencies
- Check console output for error details

**Empty Dashboard Statistics**
- Make test bookings to generate data
- Use "Refresh Data" button in admin panel
- Confirm bookings have "CONFIRMED" status

### Getting Help
1. Check console output for error messages
2. Review [Database Setup Guide](DATABASE_SETUP_GUIDE.md)
3. Verify all dependencies are installed
4. Ensure XAMPP services are running

## 🤝 Contributing

We welcome contributions to CineMate! Here's how you can help:

### Getting Started
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Make your changes
4. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
5. Push to the branch (`git push origin feature/AmazingFeature`)
6. Open a Pull Request

### Development Guidelines
- Follow Java naming conventions
- Add comments for complex logic
- Test thoroughly before submitting
- Update documentation as needed

### Areas for Contribution
- 🎨 UI/UX improvements
- 🚀 Performance optimizations
- 🔧 New features and functionality
- 🐛 Bug fixes and improvements
- 📚 Documentation enhancements

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Rie** - [@riecodes](https://github.com/riecodes)

## 🙏 Acknowledgments

- Java Swing documentation and community
- MySQL database management system
- Maven for dependency management
- All contributors and testers

---

**Ready to manage your cinema with CineMate!** 🎬✨

For detailed setup instructions, see [DATABASE_SETUP_GUIDE.md](DATABASE_SETUP_GUIDE.md) 