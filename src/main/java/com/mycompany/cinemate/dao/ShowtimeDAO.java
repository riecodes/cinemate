package com.mycompany.cinemate.dao;

import com.mycompany.cinemate.database.DatabaseConfig;
import com.mycompany.cinemate.models.Showtime;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShowtimeDAO {
    
    public List<Showtime> getAllShowtimes() {
        List<Showtime> showtimes = new ArrayList<>();
        String sql = "SELECT * FROM showtimes ORDER BY show_date, show_time";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Showtime showtime = new Showtime(
                    rs.getInt("id"),
                    rs.getInt("movie_id"),
                    rs.getDate("show_date").toLocalDate(),
                    rs.getTime("show_time").toLocalTime(),
                    rs.getInt("available_seats"),
                    rs.getTimestamp("created_at").toLocalDateTime()
                );
                showtimes.add(showtime);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all showtimes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return showtimes;
    }
    
    public List<Showtime> getShowtimesByMovieId(int movieId) {
        List<Showtime> showtimes = new ArrayList<>();
        String sql = "SELECT * FROM showtimes WHERE movie_id = ? ORDER BY show_date, show_time";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, movieId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Showtime showtime = new Showtime(
                        rs.getInt("id"),
                        rs.getInt("movie_id"),
                        rs.getDate("show_date").toLocalDate(),
                        rs.getTime("show_time").toLocalTime(),
                        rs.getInt("available_seats"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                    );
                    showtimes.add(showtime);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting showtimes by movie ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return showtimes;
    }
    
    public List<Showtime> getShowtimesByDate(LocalDate date) {
        List<Showtime> showtimes = new ArrayList<>();
        String sql = "SELECT * FROM showtimes WHERE show_date = ? ORDER BY show_time";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, Date.valueOf(date));
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Showtime showtime = new Showtime(
                        rs.getInt("id"),
                        rs.getInt("movie_id"),
                        rs.getDate("show_date").toLocalDate(),
                        rs.getTime("show_time").toLocalTime(),
                        rs.getInt("available_seats"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                    );
                    showtimes.add(showtime);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting showtimes by date: " + e.getMessage());
            e.printStackTrace();
        }
        
        return showtimes;
    }
    
    public Showtime getShowtimeById(int id) {
        String sql = "SELECT * FROM showtimes WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Showtime(
                        rs.getInt("id"),
                        rs.getInt("movie_id"),
                        rs.getDate("show_date").toLocalDate(),
                        rs.getTime("show_time").toLocalTime(),
                        rs.getInt("available_seats"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting showtime by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public List<Showtime> getAvailableShowtimes() {
        List<Showtime> showtimes = new ArrayList<>();
        String sql = "SELECT * FROM showtimes WHERE available_seats > 0 AND show_date >= CURDATE() ORDER BY show_date, show_time";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Showtime showtime = new Showtime(
                    rs.getInt("id"),
                    rs.getInt("movie_id"),
                    rs.getDate("show_date").toLocalDate(),
                    rs.getTime("show_time").toLocalTime(),
                    rs.getInt("available_seats"),
                    rs.getTimestamp("created_at").toLocalDateTime()
                );
                showtimes.add(showtime);
            }
        } catch (SQLException e) {
            System.err.println("Error getting available showtimes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return showtimes;
    }
    
    public boolean addShowtime(Showtime showtime) {
        String sql = "INSERT INTO showtimes (movie_id, show_date, show_time, available_seats) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, showtime.getMovieId());
            pstmt.setDate(2, Date.valueOf(showtime.getShowDate()));
            pstmt.setTime(3, Time.valueOf(showtime.getShowTime()));
            pstmt.setInt(4, showtime.getAvailableSeats());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding showtime: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateShowtime(Showtime showtime) {
        String sql = "UPDATE showtimes SET movie_id = ?, show_date = ?, show_time = ?, available_seats = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, showtime.getMovieId());
            pstmt.setDate(2, Date.valueOf(showtime.getShowDate()));
            pstmt.setTime(3, Time.valueOf(showtime.getShowTime()));
            pstmt.setInt(4, showtime.getAvailableSeats());
            pstmt.setInt(5, showtime.getId());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating showtime: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateAvailableSeats(int showtimeId, int newAvailableSeats) {
        String sql = "UPDATE showtimes SET available_seats = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, newAvailableSeats);
            pstmt.setInt(2, showtimeId);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating available seats: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteShowtime(int id) {
        String sql = "DELETE FROM showtimes WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting showtime: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public int getBookedSeatsCount(int showtimeId) {
        String sql = "SELECT COUNT(*) as booked_count FROM seats WHERE showtime_id = ? AND is_booked = true";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, showtimeId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("booked_count");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting booked seats count: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
} 