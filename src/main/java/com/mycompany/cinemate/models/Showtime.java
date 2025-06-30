package com.mycompany.cinemate.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.Objects;

public class Showtime {
    private int id;
    private int movieId;
    private LocalDate showDate;
    private LocalTime showTime;
    private int availableSeats;
    private LocalDateTime createdAt;
    
    // Default constructor
    public Showtime() {}
    
    // Constructor with essential fields
    public Showtime(int movieId, LocalDate showDate, LocalTime showTime, int availableSeats) {
        this.movieId = movieId;
        this.showDate = showDate;
        this.showTime = showTime;
        this.availableSeats = availableSeats;
    }
    
    // Full constructor
    public Showtime(int id, int movieId, LocalDate showDate, LocalTime showTime, int availableSeats, LocalDateTime createdAt) {
        this.id = id;
        this.movieId = movieId;
        this.showDate = showDate;
        this.showTime = showTime;
        this.availableSeats = availableSeats;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getMovieId() {
        return movieId;
    }
    
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    
    public LocalDate getShowDate() {
        return showDate;
    }
    
    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;
    }
    
    public LocalTime getShowTime() {
        return showTime;
    }
    
    public void setShowTime(LocalTime showTime) {
        this.showTime = showTime;
    }
    
    public int getAvailableSeats() {
        return availableSeats;
    }
    
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    // Helper methods
    public String getFormattedShowTime() {
        return showTime.toString();
    }
    
    public String getFormattedShowDate() {
        return showDate.toString();
    }
    
    public boolean hasAvailableSeats() {
        return availableSeats > 0;
    }
    
    public void decreaseAvailableSeats(int count) {
        this.availableSeats = Math.max(0, this.availableSeats - count);
    }
    
    public void increaseAvailableSeats(int count) {
        this.availableSeats += count;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Showtime showtime = (Showtime) obj;
        return id == showtime.id &&
               movieId == showtime.movieId &&
               Objects.equals(showDate, showtime.showDate) &&
               Objects.equals(showTime, showtime.showTime);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, movieId, showDate, showTime);
    }
    
    @Override
    public String toString() {
        return "Showtime{" +
               "id=" + id +
               ", movieId=" + movieId +
               ", showDate=" + showDate +
               ", showTime=" + showTime +
               ", availableSeats=" + availableSeats +
               ", createdAt=" + createdAt +
               '}';
    }
} 