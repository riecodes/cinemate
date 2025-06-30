package com.mycompany.cinemate.dao;

import com.mycompany.cinemate.database.DatabaseConfig;
import com.mycompany.cinemate.models.Booking;
import com.mycompany.cinemate.models.Booking.BookingStatus;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Booking booking = createBookingFromResultSet(rs);
                bookings.add(booking);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all bookings: " + e.getMessage());
            e.printStackTrace();
        }
        
        return bookings;
    }
    
    public List<Booking> getBookingsByStatus(BookingStatus status) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE booking_status = ? ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status.name());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = createBookingFromResultSet(rs);
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting bookings by status: " + e.getMessage());
            e.printStackTrace();
        }
        
        return bookings;
    }
    
    public List<Booking> getBookingsByCustomerEmail(String customerEmail) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE customer_email = ? ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, customerEmail);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = createBookingFromResultSet(rs);
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting bookings by customer email: " + e.getMessage());
            e.printStackTrace();
        }
        
        return bookings;
    }
    
    public List<Booking> getBookingsByMovieId(int movieId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE movie_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, movieId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = createBookingFromResultSet(rs);
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting bookings by movie ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return bookings;
    }
    
    public List<Booking> getBookingsByShowtimeId(int showtimeId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE showtime_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, showtimeId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = createBookingFromResultSet(rs);
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting bookings by showtime ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return bookings;
    }
    
    public Booking getBookingById(int id) {
        String sql = "SELECT * FROM bookings WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return createBookingFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting booking by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public int addBooking(Booking booking) {
        String sql = "INSERT INTO bookings (customer_name, customer_email, customer_phone, movie_id, showtime_id, booking_date, seats, total_price, booking_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, booking.getCustomerName());
            pstmt.setString(2, booking.getCustomerEmail());
            pstmt.setString(3, booking.getCustomerPhone());
            pstmt.setInt(4, booking.getMovieId());
            pstmt.setObject(5, booking.getShowtimeId() > 0 ? booking.getShowtimeId() : null);
            pstmt.setDate(6, Date.valueOf(booking.getBookingDate()));
            pstmt.setString(7, booking.getSeats());
            pstmt.setBigDecimal(8, booking.getTotalPrice());
            pstmt.setString(9, booking.getBookingStatus().name());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error adding booking: " + e.getMessage());
            e.printStackTrace();
        }
        
        return -1;
    }
    
    public boolean updateBooking(Booking booking) {
        String sql = "UPDATE bookings SET customer_name = ?, customer_email = ?, customer_phone = ?, movie_id = ?, showtime_id = ?, booking_date = ?, seats = ?, total_price = ?, booking_status = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, booking.getCustomerName());
            pstmt.setString(2, booking.getCustomerEmail());
            pstmt.setString(3, booking.getCustomerPhone());
            pstmt.setInt(4, booking.getMovieId());
            pstmt.setObject(5, booking.getShowtimeId() > 0 ? booking.getShowtimeId() : null);
            pstmt.setDate(6, Date.valueOf(booking.getBookingDate()));
            pstmt.setString(7, booking.getSeats());
            pstmt.setBigDecimal(8, booking.getTotalPrice());
            pstmt.setString(9, booking.getBookingStatus().name());
            pstmt.setInt(10, booking.getId());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating booking: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateBookingStatus(int bookingId, BookingStatus status) {
        String sql = "UPDATE bookings SET booking_status = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status.name());
            pstmt.setInt(2, bookingId);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating booking status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean confirmBooking(int bookingId) {
        return updateBookingStatus(bookingId, BookingStatus.CONFIRMED);
    }
    
    public boolean cancelBooking(int bookingId) {
        return updateBookingStatus(bookingId, BookingStatus.CANCELLED);
    }
    
    public boolean deleteBooking(int id) {
        String sql = "DELETE FROM bookings WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting booking: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public BigDecimal getTotalSales() {
        String sql = "SELECT SUM(total_price) as total_sales FROM bookings WHERE booking_status = 'CONFIRMED'";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                BigDecimal totalSales = rs.getBigDecimal("total_sales");
                return totalSales != null ? totalSales : BigDecimal.ZERO;
            }
        } catch (SQLException e) {
            System.err.println("Error getting total sales: " + e.getMessage());
            e.printStackTrace();
        }
        
        return BigDecimal.ZERO;
    }
    
    public int getTotalTicketsSold() {
        String sql = "SELECT COUNT(*) as total_tickets FROM bookings WHERE booking_status = 'CONFIRMED'";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("total_tickets");
            }
        } catch (SQLException e) {
            System.err.println("Error getting total tickets sold: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public List<Booking> getBookingsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE booking_date BETWEEN ? AND ? ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, Date.valueOf(startDate));
            pstmt.setDate(2, Date.valueOf(endDate));
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = createBookingFromResultSet(rs);
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting bookings by date range: " + e.getMessage());
            e.printStackTrace();
        }
        
        return bookings;
    }
    
    private Booking createBookingFromResultSet(ResultSet rs) throws SQLException {
        return new Booking(
            rs.getInt("id"),
            rs.getString("customer_name"),
            rs.getString("customer_email"),
            rs.getString("customer_phone"),
            rs.getInt("movie_id"),
            rs.getObject("showtime_id") != null ? rs.getInt("showtime_id") : 0,
            rs.getDate("booking_date").toLocalDate(),
            rs.getString("seats"),
            rs.getBigDecimal("total_price"),
            BookingStatus.valueOf(rs.getString("booking_status")),
            rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
} 