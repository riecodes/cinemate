package com.mycompany.cinemate.dao;

import com.mycompany.cinemate.database.DatabaseConfig;
import com.mycompany.cinemate.models.Seat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO {
    
    public List<Seat> getSeatsByShowtimeId(int showtimeId) {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM seats WHERE showtime_id = ? ORDER BY seat_row, seat_number";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, showtimeId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Seat seat = new Seat(
                        rs.getInt("id"),
                        rs.getInt("showtime_id"),
                        rs.getString("seat_row").charAt(0),
                        rs.getInt("seat_number"),
                        rs.getBoolean("is_booked")
                    );
                    seats.add(seat);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting seats by showtime ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return seats;
    }
    
    public List<Seat> getAvailableSeatsByShowtimeId(int showtimeId) {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM seats WHERE showtime_id = ? AND is_booked = false ORDER BY seat_row, seat_number";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, showtimeId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Seat seat = new Seat(
                        rs.getInt("id"),
                        rs.getInt("showtime_id"),
                        rs.getString("seat_row").charAt(0),
                        rs.getInt("seat_number"),
                        rs.getBoolean("is_booked")
                    );
                    seats.add(seat);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting available seats: " + e.getMessage());
            e.printStackTrace();
        }
        
        return seats;
    }
    
    public List<Seat> getBookedSeatsByShowtimeId(int showtimeId) {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM seats WHERE showtime_id = ? AND is_booked = true ORDER BY seat_row, seat_number";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, showtimeId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Seat seat = new Seat(
                        rs.getInt("id"),
                        rs.getInt("showtime_id"),
                        rs.getString("seat_row").charAt(0),
                        rs.getInt("seat_number"),
                        rs.getBoolean("is_booked")
                    );
                    seats.add(seat);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting booked seats: " + e.getMessage());
            e.printStackTrace();
        }
        
        return seats;
    }
    
    public Seat getSeatById(int id) {
        String sql = "SELECT * FROM seats WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Seat(
                        rs.getInt("id"),
                        rs.getInt("showtime_id"),
                        rs.getString("seat_row").charAt(0),
                        rs.getInt("seat_number"),
                        rs.getBoolean("is_booked")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting seat by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public Seat getSeat(int showtimeId, char seatRow, int seatNumber) {
        String sql = "SELECT * FROM seats WHERE showtime_id = ? AND seat_row = ? AND seat_number = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, showtimeId);
            pstmt.setString(2, String.valueOf(seatRow));
            pstmt.setInt(3, seatNumber);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Seat(
                        rs.getInt("id"),
                        rs.getInt("showtime_id"),
                        rs.getString("seat_row").charAt(0),
                        rs.getInt("seat_number"),
                        rs.getBoolean("is_booked")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting specific seat: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean addSeat(Seat seat) {
        String sql = "INSERT INTO seats (showtime_id, seat_row, seat_number, is_booked) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, seat.getShowtimeId());
            pstmt.setString(2, String.valueOf(seat.getSeatRow()));
            pstmt.setInt(3, seat.getSeatNumber());
            pstmt.setBoolean(4, seat.isBooked());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding seat: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean bookSeat(int seatId) {
        String sql = "UPDATE seats SET is_booked = true WHERE id = ? AND is_booked = false";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, seatId);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error booking seat: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean bookSeats(List<Integer> seatIds) {
        if (seatIds == null || seatIds.isEmpty()) {
            return false;
        }
        
        String sql = "UPDATE seats SET is_booked = true WHERE id = ? AND is_booked = false";
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (Integer seatId : seatIds) {
                    pstmt.setInt(1, seatId);
                    pstmt.addBatch();
                }
                
                int[] results = pstmt.executeBatch();
                
                // Check if all seats were successfully booked
                for (int result : results) {
                    if (result <= 0) {
                        conn.rollback();
                        return false;
                    }
                }
                
                conn.commit();
                return true;
                
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
            
        } catch (SQLException e) {
            System.err.println("Error booking multiple seats: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean releaseSeat(int seatId) {
        String sql = "UPDATE seats SET is_booked = false WHERE id = ? AND is_booked = true";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, seatId);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error releasing seat: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean releaseSeats(List<Integer> seatIds) {
        if (seatIds == null || seatIds.isEmpty()) {
            return false;
        }
        
        String sql = "UPDATE seats SET is_booked = false WHERE id = ? AND is_booked = true";
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (Integer seatId : seatIds) {
                    pstmt.setInt(1, seatId);
                    pstmt.addBatch();
                }
                
                pstmt.executeBatch();
                conn.commit();
                return true;
                
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
            
        } catch (SQLException e) {
            System.err.println("Error releasing multiple seats: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean isSeatAvailable(int showtimeId, char seatRow, int seatNumber) {
        String sql = "SELECT is_booked FROM seats WHERE showtime_id = ? AND seat_row = ? AND seat_number = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, showtimeId);
            pstmt.setString(2, String.valueOf(seatRow));
            pstmt.setInt(3, seatNumber);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return !rs.getBoolean("is_booked");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking seat availability: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    public int getAvailableSeatCount(int showtimeId) {
        String sql = "SELECT COUNT(*) as available_count FROM seats WHERE showtime_id = ? AND is_booked = false";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, showtimeId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("available_count");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting available seat count: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public boolean deleteSeatsForShowtime(int showtimeId) {
        String sql = "DELETE FROM seats WHERE showtime_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, showtimeId);
            return pstmt.executeUpdate() >= 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting seats for showtime: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
} 