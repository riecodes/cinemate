package com.mycompany.cinemate.dao;

import com.mycompany.cinemate.database.DatabaseConfig;
import com.mycompany.cinemate.models.TicketType;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketTypeDAO {
    
    public List<TicketType> getAllTicketTypes() {
        List<TicketType> ticketTypes = new ArrayList<>();
        String sql = "SELECT * FROM ticket_types ORDER BY type_name";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                TicketType ticketType = new TicketType(
                    rs.getInt("id"),
                    rs.getString("type_name"),
                    rs.getBigDecimal("price"),
                    rs.getBoolean("is_active"),
                    rs.getTimestamp("created_at").toLocalDateTime()
                );
                ticketTypes.add(ticketType);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all ticket types: " + e.getMessage());
            e.printStackTrace();
        }
        
        return ticketTypes;
    }
    
    public List<TicketType> getActiveTicketTypes() {
        List<TicketType> ticketTypes = new ArrayList<>();
        String sql = "SELECT * FROM ticket_types WHERE is_active = true ORDER BY type_name";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                TicketType ticketType = new TicketType(
                    rs.getInt("id"),
                    rs.getString("type_name"),
                    rs.getBigDecimal("price"),
                    rs.getBoolean("is_active"),
                    rs.getTimestamp("created_at").toLocalDateTime()
                );
                ticketTypes.add(ticketType);
            }
        } catch (SQLException e) {
            System.err.println("Error getting active ticket types: " + e.getMessage());
            e.printStackTrace();
        }
        
        return ticketTypes;
    }
    
    public TicketType getTicketTypeById(int id) {
        String sql = "SELECT * FROM ticket_types WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new TicketType(
                        rs.getInt("id"),
                        rs.getString("type_name"),
                        rs.getBigDecimal("price"),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting ticket type by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public TicketType getTicketTypeByName(String typeName) {
        String sql = "SELECT * FROM ticket_types WHERE type_name = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, typeName);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new TicketType(
                        rs.getInt("id"),
                        rs.getString("type_name"),
                        rs.getBigDecimal("price"),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting ticket type by name: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean addTicketType(TicketType ticketType) {
        String sql = "INSERT INTO ticket_types (type_name, price, is_active) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ticketType.getTypeName());
            pstmt.setBigDecimal(2, ticketType.getPrice());
            pstmt.setBoolean(3, ticketType.isActive());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding ticket type: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateTicketType(TicketType ticketType) {
        String sql = "UPDATE ticket_types SET type_name = ?, price = ?, is_active = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ticketType.getTypeName());
            pstmt.setBigDecimal(2, ticketType.getPrice());
            pstmt.setBoolean(3, ticketType.isActive());
            pstmt.setInt(4, ticketType.getId());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating ticket type: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateTicketTypePrice(int id, BigDecimal newPrice) {
        String sql = "UPDATE ticket_types SET price = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setBigDecimal(1, newPrice);
            pstmt.setInt(2, id);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating ticket type price: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean activateTicketType(int id) {
        String sql = "UPDATE ticket_types SET is_active = true WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error activating ticket type: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deactivateTicketType(int id) {
        String sql = "UPDATE ticket_types SET is_active = false WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deactivating ticket type: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteTicketType(int id) {
        String sql = "DELETE FROM ticket_types WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting ticket type: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean ticketTypeExists(String typeName) {
        String sql = "SELECT COUNT(*) as count FROM ticket_types WHERE type_name = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, typeName);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count") > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking if ticket type exists: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    public BigDecimal getTicketTypePrice(int id) {
        String sql = "SELECT price FROM ticket_types WHERE id = ? AND is_active = true";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("price");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting ticket type price: " + e.getMessage());
            e.printStackTrace();
        }
        
        return BigDecimal.ZERO;
    }
} 