package com.mycompany.cinemate.database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseSchema {
    
    public static void initializeDatabase() {
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            
            System.out.println("Creating database and tables...");
            
            // Create database if not exists
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS cinemate_db");
            stmt.executeUpdate("USE cinemate_db");
            
            // Create movies table first (no dependencies)
            createMoviesTable(stmt);
            System.out.println("Movies table created/verified");
            
            // Create users table (no dependencies)
            createUsersTable(stmt);
            System.out.println("Users table created/verified");
            
            // Create ticket types table (no dependencies)
            createTicketTypesTable(stmt);
            System.out.println("Ticket types table created/verified");
            
            // Create showtimes table (depends on movies)
            createShowtimesTable(stmt);
            System.out.println("Showtimes table created/verified");
            
            // Create seats table (depends on showtimes)
            createSeatsTable(stmt);
            System.out.println("Seats table created/verified");
            
            // Create bookings table (depends on movies and showtimes)
            createBookingsTable(stmt);
            System.out.println("Bookings table created/verified");
            
            // Create booking items table
            createBookingItemsTable(stmt);
            System.out.println("Booking items table created/verified");
            
            // Create booking seats table
            createBookingSeatsTable(stmt);
            System.out.println("Booking seats table created/verified");
            
            // Insert initial movie data
            insertInitialMovieData(stmt);
            System.out.println("Initial movie data loaded");
            
            // Insert ticket types
            insertTicketTypes(stmt);
            System.out.println("Ticket types loaded");
            
            // Insert sample showtimes
            insertSampleShowtimes(stmt);
            System.out.println("Sample showtimes loaded");
            
            // Insert seats for showtimes
            insertSeatsForShowtimes(stmt);
            System.out.println("Seats initialized");
            
            // Insert sample booking data
            insertSampleBookingData(stmt);
            System.out.println("Sample booking data loaded");
            
            // Insert sample user data
            insertSampleUserData(stmt);
            System.out.println("Sample user data loaded");
            
            System.out.println("✓ Database schema initialized successfully!");
            
        } catch (SQLException e) {
            System.err.println("✗ Error initializing database schema: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void createMoviesTable(Statement stmt) throws SQLException {
        String createMoviesSQL = """
            CREATE TABLE IF NOT EXISTS movies (
                id INT AUTO_INCREMENT PRIMARY KEY,
                title VARCHAR(255) NOT NULL,
                description TEXT,
                start_date DATE NOT NULL,
                end_date DATE NOT NULL,
                price DECIMAL(10, 2) NOT NULL,
                poster_path VARCHAR(500),
                is_active BOOLEAN DEFAULT TRUE,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
            )
            """;
        stmt.executeUpdate(createMoviesSQL);
    }
    
    private static void createBookingsTable(Statement stmt) throws SQLException {
        String createBookingsSQL = """
            CREATE TABLE IF NOT EXISTS bookings (
                id INT AUTO_INCREMENT PRIMARY KEY,
                customer_name VARCHAR(255) NOT NULL,
                customer_email VARCHAR(255),
                customer_phone VARCHAR(20),
                movie_id INT NOT NULL,
                showtime_id INT,
                booking_date DATE NOT NULL,
                seats VARCHAR(255),
                total_price DECIMAL(10, 2) NOT NULL,
                booking_status ENUM('PENDING', 'CONFIRMED', 'CANCELLED') DEFAULT 'PENDING',
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE,
                FOREIGN KEY (showtime_id) REFERENCES showtimes(id)
            )
            """;
        stmt.executeUpdate(createBookingsSQL);
    }
    
    private static void createUsersTable(Statement stmt) throws SQLException {
        String createUsersSQL = """
            CREATE TABLE IF NOT EXISTS users (
                id INT AUTO_INCREMENT PRIMARY KEY,
                username VARCHAR(255) UNIQUE NOT NULL,
                email VARCHAR(255) UNIQUE NOT NULL,
                registration_date DATE NOT NULL,
                total_bookings INT DEFAULT 0,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
        stmt.executeUpdate(createUsersSQL);
    }
    
    private static void createShowtimesTable(Statement stmt) throws SQLException {
        String createShowtimesSQL = """
            CREATE TABLE IF NOT EXISTS showtimes (
                id INT AUTO_INCREMENT PRIMARY KEY,
                movie_id INT NOT NULL,
                show_date DATE NOT NULL,
                show_time TIME NOT NULL,
                available_seats INT DEFAULT 48,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE
            )
            """;
        stmt.executeUpdate(createShowtimesSQL);
    }
    
    private static void createSeatsTable(Statement stmt) throws SQLException {
        String createSeatsSQL = """
            CREATE TABLE IF NOT EXISTS seats (
                id INT AUTO_INCREMENT PRIMARY KEY,
                showtime_id INT NOT NULL,
                seat_row CHAR(1) NOT NULL,
                seat_number INT NOT NULL,
                is_booked BOOLEAN DEFAULT FALSE,
                UNIQUE(showtime_id, seat_row, seat_number),
                FOREIGN KEY (showtime_id) REFERENCES showtimes(id) ON DELETE CASCADE
            )
            """;
        stmt.executeUpdate(createSeatsSQL);
    }
    
    private static void createTicketTypesTable(Statement stmt) throws SQLException {
        String createTicketTypesSQL = """
            CREATE TABLE IF NOT EXISTS ticket_types (
                id INT AUTO_INCREMENT PRIMARY KEY,
                type_name VARCHAR(50) NOT NULL,
                price DECIMAL(10, 2) NOT NULL,
                is_active BOOLEAN DEFAULT TRUE,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
        stmt.executeUpdate(createTicketTypesSQL);
    }
    
    private static void createBookingItemsTable(Statement stmt) throws SQLException {
        String createBookingItemsSQL = """
            CREATE TABLE IF NOT EXISTS booking_items (
                id INT AUTO_INCREMENT PRIMARY KEY,
                booking_id INT NOT NULL,
                ticket_type_id INT NOT NULL,
                quantity INT NOT NULL,
                unit_price DECIMAL(10, 2) NOT NULL,
                subtotal DECIMAL(10, 2) NOT NULL,
                FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE,
                FOREIGN KEY (ticket_type_id) REFERENCES ticket_types(id)
            )
            """;
        stmt.executeUpdate(createBookingItemsSQL);
    }
    
    private static void createBookingSeatsTable(Statement stmt) throws SQLException {
        String createBookingSeatsSQL = """
            CREATE TABLE IF NOT EXISTS booking_seats (
                id INT AUTO_INCREMENT PRIMARY KEY,
                booking_id INT NOT NULL,
                seat_id INT NOT NULL,
                UNIQUE(seat_id),
                FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE,
                FOREIGN KEY (seat_id) REFERENCES seats(id)
            )
            """;
        stmt.executeUpdate(createBookingSeatsSQL);
    }
    
    private static void insertInitialMovieData(Statement stmt) throws SQLException {
        // Check if movies already exist
        String checkSQL = "SELECT COUNT(*) as count FROM movies";
        ResultSet rs = stmt.executeQuery(checkSQL);
        if (rs.next() && rs.getInt("count") > 0) {
            rs.close();
            return; // Data already exists
        }
        rs.close();
        
        String insertMoviesSQL = """
            INSERT INTO movies (title, description, start_date, end_date, price, poster_path, is_active) VALUES
            ('How To Train Your Dragon', 'A live-action adaptation of the beloved animated classic, this film follows the unlikely friendship between a young Viking named Hiccup and a dragon named Toothless. Together, they challenge long-standing myths and change their world forever.', '2025-06-29', '2025-07-15', 290.00, '/assets/how to train your dragon poster.png', true),
            ('Megan 2.0', 'The terrifying AI doll returns in this chilling sequel. After the events of the first film, M3GAN is reactivated—smarter, deadlier, and harder to control. With her new programming evolving beyond human understanding, no one is safe.', '2025-06-30', '2025-07-20', 310.00, '/assets/Megan2.png', true),
            ('Lilo & Stitch', 'This heartfelt live-action remake reintroduces the story of a lonely Hawaiian girl named Lilo who adopts a mysterious alien creature, Stitch. What begins as chaos transforms into a beautiful lesson about family, love, and ohana.', '2025-06-30', '2025-07-20', 250.00, '/assets/lilo and stitch poster.png', true),
            ('Ballerina', 'Set in the high-octane John Wick universe, Ballerina follows Rooney (Ana de Armas), a deadly assassin trained in ballet, as she seeks revenge for her family''s murder. Graceful yet brutal, her journey uncovers deep secrets and powerful enemies.', '2025-06-30', '2025-07-20', 360.00, '/assets/ballerina poster.png', true),
            ('Elio', 'An imaginative boy named Elio is accidentally beamed into space and mistakenly appointed as Earth''s ambassador to a galaxy full of aliens. A charming coming-of-age story from Pixar about identity, bravery, and finding one''s voice in a strange universe.', '2025-06-30', '2025-07-20', 230.00, '/assets/elio poster.png', true),
            ('Pelikulaya: Young Hearts', 'A Filipino youth-centered film showcasing diverse love stories and modern struggles, set in a vibrant campus environment. This entry in the Pelikulaya series captures the joys and heartbreaks of young adulthood with authenticity and cultural flair.', '2025-06-30', '2025-07-20', 250.00, '/assets/young hearts poster.png', true),
            ('Only We Know', 'A romantic mystery that follows two soulmates who keep finding each other in different lifetimes. As they uncover pieces of their pasts, they realize some love stories are destined to repeat—until they finally get it right.', '2025-06-30', '2025-07-20', 200.00, '/assets/only we know poster.png', true),
            ('Unconditional', 'A heart-tugging drama about the unbreakable bond between a mother and her child. As life tests their limits, they discover that true love is not based on conditions—but on sacrifice, understanding, and unwavering support.', '2025-06-30', '2025-07-20', 200.00, '/assets/uncoditional poster.png', true),
            ('28 Years Later', 'The long-awaited sequel to 28 Days Later and 28 Weeks Later. The rage virus resurfaces, more evolved and deadlier than ever. Survivors must navigate a ruined world as civilization teeters on the edge of total collapse.', '2025-06-30', '2025-07-20', 200.00, '/assets/28 years later poster.png', true),
            ('The Ritual', 'A psychological horror film that follows a group of friends who become lost in a Scandinavian forest, where they encounter ancient and terrifying forces. As paranoia sets in and the group begins to fracture, they must face both supernatural threats and their own darkest fears.', '2025-06-30', '2025-07-20', 200.00, '/assets/the ritual poster.png', true)
            """;
        stmt.executeUpdate(insertMoviesSQL);
    }
    
    private static void insertSampleBookingData(Statement stmt) throws SQLException {
        // Check if bookings already exist
        String checkSQL = "SELECT COUNT(*) as count FROM bookings";
        ResultSet rs = stmt.executeQuery(checkSQL);
        if (rs.next() && rs.getInt("count") > 0) {
            rs.close();
            return; // Data already exists
        }
        rs.close();
        
        // No sample booking data - bookings will be created when customers make reservations
        System.out.println("No sample booking data inserted - bookings start empty");
    }
    
    private static void insertSampleUserData(Statement stmt) throws SQLException {
        // Check if users already exist
        String checkSQL = "SELECT COUNT(*) as count FROM users";
        ResultSet rs = stmt.executeQuery(checkSQL);
        if (rs.next() && rs.getInt("count") > 0) {
            rs.close();
            return; // Data already exists
        }
        rs.close();
        
        String insertUsersSQL = """
            INSERT INTO users (username, email, registration_date, total_bookings) VALUES
            ('user', 'user@email.com', '2025-05-15', 0)
            """;
        stmt.executeUpdate(insertUsersSQL);
    }
    
    private static void insertTicketTypes(Statement stmt) throws SQLException {
        // Check if ticket types already exist
        String checkSQL = "SELECT COUNT(*) as count FROM ticket_types";
        ResultSet rs = stmt.executeQuery(checkSQL);
        if (rs.next() && rs.getInt("count") > 0) {
            rs.close();
            return; // Data already exists
        }
        rs.close();
        
        String insertTicketTypesSQL = """
            INSERT INTO ticket_types (type_name, price, is_active) VALUES
            ('2D REGULAR', 290.00, true),
            ('3D PREMIUM', 390.00, true),
            ('IMAX', 490.00, true)
            """;
        stmt.executeUpdate(insertTicketTypesSQL);
    }
    
    private static void insertSampleShowtimes(Statement stmt) throws SQLException {
        // Check if showtimes already exist
        String checkSQL = "SELECT COUNT(*) as count FROM showtimes";
        ResultSet rs = stmt.executeQuery(checkSQL);
        if (rs.next() && rs.getInt("count") > 0) {
            rs.close();
            return; // Data already exists
        }
        rs.close();
        
        // Insert showtimes for all movies (3 showtimes per day for multiple days) - Starting from today (June 29, 2025)
        String insertShowtimesSQL = """
            INSERT INTO showtimes (movie_id, show_date, show_time, available_seats) VALUES
            (1, '2025-06-29', '10:00:00', 48), (1, '2025-06-29', '14:00:00', 48), (1, '2025-06-29', '18:00:00', 48),
            (1, '2025-06-30', '10:00:00', 48), (1, '2025-06-30', '14:00:00', 48), (1, '2025-06-30', '18:00:00', 48),
            (1, '2025-07-01', '10:00:00', 48), (1, '2025-07-01', '14:00:00', 48), (1, '2025-07-01', '18:00:00', 48),
            (2, '2025-06-30', '10:30:00', 48), (2, '2025-06-30', '14:30:00', 48), (2, '2025-06-30', '18:30:00', 48),
            (2, '2025-07-01', '10:30:00', 48), (2, '2025-07-01', '14:30:00', 48), (2, '2025-07-01', '18:30:00', 48),
            (2, '2025-07-02', '10:30:00', 48), (2, '2025-07-02', '14:30:00', 48), (2, '2025-07-02', '18:30:00', 48),
            (3, '2025-06-30', '11:00:00', 48), (3, '2025-06-30', '15:00:00', 48), (3, '2025-06-30', '19:00:00', 48),
            (3, '2025-07-01', '11:00:00', 48), (3, '2025-07-01', '15:00:00', 48), (3, '2025-07-01', '19:00:00', 48),
            (3, '2025-07-02', '11:00:00', 48), (3, '2025-07-02', '15:00:00', 48), (3, '2025-07-02', '19:00:00', 48),
            (4, '2025-06-30', '09:30:00', 48), (4, '2025-06-30', '13:30:00', 48), (4, '2025-06-30', '17:30:00', 48),
            (4, '2025-07-01', '09:30:00', 48), (4, '2025-07-01', '13:30:00', 48), (4, '2025-07-01', '17:30:00', 48),
            (4, '2025-07-02', '09:30:00', 48), (4, '2025-07-02', '13:30:00', 48), (4, '2025-07-02', '17:30:00', 48),
            (5, '2025-06-30', '12:00:00', 48), (5, '2025-06-30', '16:00:00', 48), (5, '2025-06-30', '20:00:00', 48),
            (5, '2025-07-01', '12:00:00', 48), (5, '2025-07-01', '16:00:00', 48), (5, '2025-07-01', '20:00:00', 48),
            (5, '2025-07-02', '12:00:00', 48), (5, '2025-07-02', '16:00:00', 48), (5, '2025-07-02', '20:00:00', 48),
            (6, '2025-06-30', '11:30:00', 48), (6, '2025-06-30', '15:30:00', 48), (6, '2025-06-30', '19:30:00', 48),
            (6, '2025-07-01', '11:30:00', 48), (6, '2025-07-01', '15:30:00', 48), (6, '2025-07-01', '19:30:00', 48),
            (6, '2025-07-02', '11:30:00', 48), (6, '2025-07-02', '15:30:00', 48), (6, '2025-07-02', '19:30:00', 48),
            (7, '2025-06-30', '10:15:00', 48), (7, '2025-06-30', '14:15:00', 48), (7, '2025-06-30', '18:15:00', 48),
            (7, '2025-07-01', '10:15:00', 48), (7, '2025-07-01', '14:15:00', 48), (7, '2025-07-01', '18:15:00', 48),
            (7, '2025-07-02', '10:15:00', 48), (7, '2025-07-02', '14:15:00', 48), (7, '2025-07-02', '18:15:00', 48),
            (8, '2025-06-30', '12:30:00', 48), (8, '2025-06-30', '16:30:00', 48), (8, '2025-06-30', '20:30:00', 48),
            (8, '2025-07-01', '12:30:00', 48), (8, '2025-07-01', '16:30:00', 48), (8, '2025-07-01', '20:30:00', 48),
            (8, '2025-07-02', '12:30:00', 48), (8, '2025-07-02', '16:30:00', 48), (8, '2025-07-02', '20:30:00', 48),
            (9, '2025-06-30', '09:45:00', 48), (9, '2025-06-30', '13:45:00', 48), (9, '2025-06-30', '17:45:00', 48),
            (9, '2025-07-01', '09:45:00', 48), (9, '2025-07-01', '13:45:00', 48), (9, '2025-07-01', '17:45:00', 48),
            (9, '2025-07-02', '09:45:00', 48), (9, '2025-07-02', '13:45:00', 48), (9, '2025-07-02', '17:45:00', 48),
            (10, '2025-06-30', '21:00:00', 48), (10, '2025-06-30', '23:30:00', 48),
            (10, '2025-07-01', '21:00:00', 48), (10, '2025-07-01', '23:30:00', 48),
            (10, '2025-07-02', '21:00:00', 48), (10, '2025-07-02', '23:30:00', 48)
            """;
        stmt.executeUpdate(insertShowtimesSQL);
    }
    
    private static void insertSeatsForShowtimes(Statement stmt) throws SQLException {
        // Check if seats already exist
        String checkSQL = "SELECT COUNT(*) as count FROM seats";
        ResultSet rs = stmt.executeQuery(checkSQL);
        if (rs.next() && rs.getInt("count") > 0) {
            rs.close();
            return; // Data already exists
        }
        rs.close();
        
        // Get all showtimes
        String getShowtimesSQL = "SELECT id FROM showtimes";
        ResultSet showtimesRs = stmt.executeQuery(getShowtimesSQL);
        
        while (showtimesRs.next()) {
            int showtimeId = showtimesRs.getInt("id");
            
            // Create seats for each showtime (6 rows: A-F, 8 seats per row)
            for (char row = 'A'; row <= 'F'; row++) {
                for (int seatNum = 1; seatNum <= 8; seatNum++) {
                    String insertSeatSQL = "INSERT INTO seats (showtime_id, seat_row, seat_number, is_booked) VALUES (?, ?, ?, false)";
                    try (var pstmt = stmt.getConnection().prepareStatement(insertSeatSQL)) {
                        pstmt.setInt(1, showtimeId);
                        pstmt.setString(2, String.valueOf(row));
                        pstmt.setInt(3, seatNum);
                        pstmt.executeUpdate();
                    }
                }
            }
        }
        showtimesRs.close();
    }
    
    public static void dropAllTables() {
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Drop tables in reverse order due to foreign key constraints
            stmt.executeUpdate("DROP TABLE IF EXISTS booking_seats");
            stmt.executeUpdate("DROP TABLE IF EXISTS booking_items");
            stmt.executeUpdate("DROP TABLE IF EXISTS bookings");
            stmt.executeUpdate("DROP TABLE IF EXISTS seats");
            stmt.executeUpdate("DROP TABLE IF EXISTS showtimes");
            stmt.executeUpdate("DROP TABLE IF EXISTS ticket_types");
            stmt.executeUpdate("DROP TABLE IF EXISTS users");
            stmt.executeUpdate("DROP TABLE IF EXISTS movies");
            
            System.out.println("All tables dropped successfully!");
            
        } catch (SQLException e) {
            System.err.println("Error dropping tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void resetDatabase() {
        System.out.println("Resetting database with latest schema...");
        dropAllTables();
        initializeDatabase();
    }
} 