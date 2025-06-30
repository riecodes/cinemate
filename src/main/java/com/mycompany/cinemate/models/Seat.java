package com.mycompany.cinemate.models;

import java.util.Objects;

public class Seat {
    private int id;
    private int showtimeId;
    private char seatRow;
    private int seatNumber;
    private boolean isBooked;
    
    // Default constructor
    public Seat() {}
    
    // Constructor with essential fields
    public Seat(int showtimeId, char seatRow, int seatNumber) {
        this.showtimeId = showtimeId;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.isBooked = false;
    }
    
    // Full constructor
    public Seat(int id, int showtimeId, char seatRow, int seatNumber, boolean isBooked) {
        this.id = id;
        this.showtimeId = showtimeId;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.isBooked = isBooked;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getShowtimeId() {
        return showtimeId;
    }
    
    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }
    
    public char getSeatRow() {
        return seatRow;
    }
    
    public void setSeatRow(char seatRow) {
        this.seatRow = seatRow;
    }
    
    public int getSeatNumber() {
        return seatNumber;
    }
    
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
    
    public boolean isBooked() {
        return isBooked;
    }
    
    public void setBooked(boolean booked) {
        isBooked = booked;
    }
    
    // Helper methods
    public String getSeatLabel() {
        return seatRow + String.valueOf(seatNumber);
    }
    
    public boolean isAvailable() {
        return !isBooked;
    }
    
    public void book() {
        this.isBooked = true;
    }
    
    public void release() {
        this.isBooked = false;
    }
    
    // Get row index (A=0, B=1, etc.)
    public int getRowIndex() {
        return seatRow - 'A';
    }
    
    // Get column index (1-based to 0-based)
    public int getColumnIndex() {
        return seatNumber - 1;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Seat seat = (Seat) obj;
        return id == seat.id &&
               showtimeId == seat.showtimeId &&
               seatRow == seat.seatRow &&
               seatNumber == seat.seatNumber;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, showtimeId, seatRow, seatNumber);
    }
    
    @Override
    public String toString() {
        return "Seat{" +
               "id=" + id +
               ", showtimeId=" + showtimeId +
               ", seatRow=" + seatRow +
               ", seatNumber=" + seatNumber +
               ", isBooked=" + isBooked +
               '}';
    }
} 