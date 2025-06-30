package com.mycompany.cinemate.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Movie {
    private int id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private String posterPath;
    private boolean isActive;
    
    // Default constructor
    public Movie() {}
    
    // Constructor with all fields
    public Movie(int id, String title, String description, LocalDate startDate, 
                 LocalDate endDate, BigDecimal price, String posterPath, boolean isActive) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.posterPath = posterPath;
        this.isActive = isActive;
    }
    
    // Constructor without ID (for new movies)
    public Movie(String title, String description, LocalDate startDate, 
                 LocalDate endDate, BigDecimal price, String posterPath, boolean isActive) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.posterPath = posterPath;
        this.isActive = isActive;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public String getPosterPath() {
        return posterPath;
    }
    
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    // Helper methods for formatting
    public String getFormattedStartDate() {
        return startDate != null ? startDate.toString() : "N/A";
    }
    
    public String getFormattedEndDate() {
        return endDate != null ? endDate.toString() : "N/A";
    }
    
    public String getFormattedPrice() {
        return "₱" + price.toString();
    }
    
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                ", posterPath='" + posterPath + '\'' +
                ", isActive=" + isActive +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Movie movie = (Movie) o;
        return id == movie.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
} 