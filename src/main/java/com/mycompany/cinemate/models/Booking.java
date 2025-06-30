package com.mycompany.cinemate.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Booking {
    private int id;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private int movieId;
    private int showtimeId;
    private LocalDate bookingDate;
    private String seats;
    private BigDecimal totalPrice;
    private BookingStatus bookingStatus;
    private LocalDateTime createdAt;
    
    // Booking status enum
    public enum BookingStatus {
        PENDING, CONFIRMED, CANCELLED
    }
    
    // Default constructor
    public Booking() {}
    
    // Constructor with essential fields
    public Booking(String customerName, String customerEmail, String customerPhone, 
                   int movieId, int showtimeId, LocalDate bookingDate, String seats, BigDecimal totalPrice) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.movieId = movieId;
        this.showtimeId = showtimeId;
        this.bookingDate = bookingDate;
        this.seats = seats;
        this.totalPrice = totalPrice;
        this.bookingStatus = BookingStatus.PENDING;
    }
    
    // Full constructor
    public Booking(int id, String customerName, String customerEmail, String customerPhone,
                   int movieId, int showtimeId, LocalDate bookingDate, String seats, 
                   BigDecimal totalPrice, BookingStatus bookingStatus, LocalDateTime createdAt) {
        this.id = id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.movieId = movieId;
        this.showtimeId = showtimeId;
        this.bookingDate = bookingDate;
        this.seats = seats;
        this.totalPrice = totalPrice;
        this.bookingStatus = bookingStatus;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerEmail() {
        return customerEmail;
    }
    
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    
    public String getCustomerPhone() {
        return customerPhone;
    }
    
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
    
    public int getMovieId() {
        return movieId;
    }
    
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    
    public int getShowtimeId() {
        return showtimeId;
    }
    
    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }
    
    public LocalDate getBookingDate() {
        return bookingDate;
    }
    
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
    
    public String getSeats() {
        return seats;
    }
    
    public void setSeats(String seats) {
        this.seats = seats;
    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }
    
    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    // Helper methods
    public String getFormattedTotalPrice() {
        return "₱" + totalPrice.toString();
    }
    
    public boolean isPending() {
        return bookingStatus == BookingStatus.PENDING;
    }
    
    public boolean isConfirmed() {
        return bookingStatus == BookingStatus.CONFIRMED;
    }
    
    public boolean isCancelled() {
        return bookingStatus == BookingStatus.CANCELLED;
    }
    
    public void confirm() {
        this.bookingStatus = BookingStatus.CONFIRMED;
    }
    
    public void cancel() {
        this.bookingStatus = BookingStatus.CANCELLED;
    }
    
    public String[] getSeatArray() {
        if (seats == null || seats.trim().isEmpty()) {
            return new String[0];
        }
        return seats.split(",");
    }
    
    public int getSeatCount() {
        return getSeatArray().length;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Booking booking = (Booking) obj;
        return id == booking.id &&
               movieId == booking.movieId &&
               showtimeId == booking.showtimeId &&
               Objects.equals(customerEmail, booking.customerEmail) &&
               Objects.equals(bookingDate, booking.bookingDate);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, movieId, showtimeId, customerEmail, bookingDate);
    }
    
    @Override
    public String toString() {
        return "Booking{" +
               "id=" + id +
               ", customerName='" + customerName + '\'' +
               ", customerEmail='" + customerEmail + '\'' +
               ", customerPhone='" + customerPhone + '\'' +
               ", movieId=" + movieId +
               ", showtimeId=" + showtimeId +
               ", bookingDate=" + bookingDate +
               ", seats='" + seats + '\'' +
               ", totalPrice=" + totalPrice +
               ", bookingStatus=" + bookingStatus +
               ", createdAt=" + createdAt +
               '}';
    }
} 