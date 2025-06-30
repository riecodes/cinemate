package com.mycompany.cinemate.dao;

import com.mycompany.cinemate.database.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;

public class StatisticsDAO {
    
    // Get total sales from all confirmed bookings
    public BigDecimal getTotalSales() {
        String sql = "SELECT COALESCE(SUM(total_price), 0) as total_sales FROM bookings WHERE booking_status = 'CONFIRMED'";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getBigDecimal("total_sales");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting total sales: " + e.getMessage());
            e.printStackTrace();
        }
        
        return BigDecimal.ZERO;
    }
    
    // Get total number of tickets sold (confirmed bookings)
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
    
    // Get number of active movies currently showing
    public int getNumberOfShowings() {
        String sql = "SELECT COUNT(*) as total_showings FROM movies WHERE is_active = true AND start_date <= CURDATE() AND end_date >= CURDATE()";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("total_showings");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting number of showings: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    // Get total number of users registered
    public int getTotalUsers() {
        String sql = "SELECT COUNT(*) as total_users FROM users";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("total_users");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting total users: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    // Get total bookings (all statuses)
    public int getTotalBookings() {
        String sql = "SELECT COUNT(*) as total_bookings FROM bookings";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("total_bookings");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting total bookings: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    // Get pending bookings count
    public int getPendingBookings() {
        String sql = "SELECT COUNT(*) as pending_bookings FROM bookings WHERE booking_status = 'PENDING'";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("pending_bookings");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting pending bookings: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    // Get average ticket price
    public BigDecimal getAverageTicketPrice() {
        String sql = "SELECT COALESCE(AVG(price), 0) as avg_price FROM movies WHERE is_active = true";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getBigDecimal("avg_price");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting average ticket price: " + e.getMessage());
            e.printStackTrace();
        }
        
        return BigDecimal.ZERO;
    }
    
    // Get most popular movie (by booking count)
    public String getMostPopularMovie() {
        String sql = """
            SELECT m.title, COUNT(b.id) as booking_count 
            FROM movies m 
            LEFT JOIN bookings b ON m.id = b.movie_id 
            WHERE m.is_active = true 
            GROUP BY m.id, m.title 
            ORDER BY booking_count DESC 
            LIMIT 1
            """;
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getString("title");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting most popular movie: " + e.getMessage());
            e.printStackTrace();
        }
        
        return "No Data";
    }
} 