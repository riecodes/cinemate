package com.mycompany.cinemate.dao;

import com.mycompany.cinemate.database.DatabaseConfig;
import com.mycompany.cinemate.models.Movie;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class MovieDAO {
    
    // Get all active movies
    public List<Movie> getAllActiveMovies() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies WHERE is_active = true ORDER BY start_date";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                movies.add(mapResultSetToMovie(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching active movies: " + e.getMessage());
            e.printStackTrace();
        }
        
        return movies;
    }
    
    // Get all movies (including inactive)
    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                movies.add(mapResultSetToMovie(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching all movies: " + e.getMessage());
            e.printStackTrace();
        }
        
        return movies;
    }
    
    // Get movie by ID
    public Movie getMovieById(int id) {
        String sql = "SELECT * FROM movies WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToMovie(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching movie by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Get movie by title
    public Movie getMovieByTitle(String title) {
        String sql = "SELECT * FROM movies WHERE title = ? LIMIT 1";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToMovie(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching movie by title: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Add new movie
    public boolean addMovie(Movie movie) {
        String sql = "INSERT INTO movies (title, description, start_date, end_date, price, poster_path, is_active) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getDescription());
            pstmt.setDate(3, Date.valueOf(movie.getStartDate()));
            pstmt.setDate(4, Date.valueOf(movie.getEndDate()));
            pstmt.setBigDecimal(5, movie.getPrice());
            pstmt.setString(6, movie.getPosterPath());
            pstmt.setBoolean(7, movie.isActive());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                // Get the generated ID
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    movie.setId(generatedKeys.getInt(1));
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error adding movie: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Update existing movie
    public boolean updateMovie(Movie movie) {
        String sql = "UPDATE movies SET title = ?, description = ?, start_date = ?, end_date = ?, price = ?, poster_path = ?, is_active = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getDescription());
            pstmt.setDate(3, Date.valueOf(movie.getStartDate()));
            pstmt.setDate(4, Date.valueOf(movie.getEndDate()));
            pstmt.setBigDecimal(5, movie.getPrice());
            pstmt.setString(6, movie.getPosterPath());
            pstmt.setBoolean(7, movie.isActive());
            pstmt.setInt(8, movie.getId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating movie: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Delete movie (soft delete - set inactive)
    public boolean deleteMovie(int movieId) {
        String sql = "UPDATE movies SET is_active = false WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, movieId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting movie: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Permanently delete movie from database
    public boolean permanentDeleteMovie(int movieId) {
        String sql = "DELETE FROM movies WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, movieId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error permanently deleting movie: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Get total number of active movies
    public int getTotalActiveMovies() {
        String sql = "SELECT COUNT(*) as total FROM movies WHERE is_active = true";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting total active movies: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    // Get movies showing today
    public List<Movie> getMoviesShowingToday() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies WHERE is_active = true AND start_date <= CURDATE() AND end_date >= CURDATE() ORDER BY start_date";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                movies.add(mapResultSetToMovie(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching movies showing today: " + e.getMessage());
            e.printStackTrace();
        }
        
        return movies;
    }
    
    // Reactivate movie (set back to active)
    public boolean reactivateMovie(int movieId) {
        String sql = "UPDATE movies SET is_active = true WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, movieId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error reactivating movie: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Helper method to map ResultSet to Movie object
    private Movie mapResultSetToMovie(ResultSet rs) throws SQLException {
        Movie movie = new Movie();
        movie.setId(rs.getInt("id"));
        movie.setTitle(rs.getString("title"));
        movie.setDescription(rs.getString("description"));
        movie.setStartDate(rs.getDate("start_date").toLocalDate());
        movie.setEndDate(rs.getDate("end_date").toLocalDate());
        movie.setPrice(rs.getBigDecimal("price"));
        movie.setPosterPath(rs.getString("poster_path"));
        movie.setActive(rs.getBoolean("is_active"));
        return movie;
    }
} 