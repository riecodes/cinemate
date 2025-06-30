package com.mycompany.cinemate.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class TicketType {
    private int id;
    private String typeName;
    private BigDecimal price;
    private boolean isActive;
    private LocalDateTime createdAt;
    
    // Default constructor
    public TicketType() {}
    
    // Constructor with essential fields
    public TicketType(String typeName, BigDecimal price) {
        this.typeName = typeName;
        this.price = price;
        this.isActive = true;
    }
    
    // Full constructor
    public TicketType(int id, String typeName, BigDecimal price, boolean isActive, LocalDateTime createdAt) {
        this.id = id;
        this.typeName = typeName;
        this.price = price;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTypeName() {
        return typeName;
    }
    
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    // Helper methods
    public String getFormattedPrice() {
        return "₱" + price.toString();
    }
    
    public BigDecimal calculateSubtotal(int quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
    
    public boolean isAvailable() {
        return isActive;
    }
    
    public void activate() {
        this.isActive = true;
    }
    
    public void deactivate() {
        this.isActive = false;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TicketType that = (TicketType) obj;
        return id == that.id &&
               Objects.equals(typeName, that.typeName) &&
               Objects.equals(price, that.price);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, typeName, price);
    }
    
    @Override
    public String toString() {
        return "TicketType{" +
               "id=" + id +
               ", typeName='" + typeName + '\'' +
               ", price=" + price +
               ", isActive=" + isActive +
               ", createdAt=" + createdAt +
               '}';
    }
} 