package com.mycompany.cinemate.database;

public class DatabaseReset {
    
    public static void main(String[] args) {
        System.out.println("=== CineMate Database Reset Utility ===");
        System.out.println("This will drop all tables and recreate them with fresh data.");
        
        try {
            DatabaseSchema.resetDatabase();
            System.out.println("✓ Database reset completed successfully!");
            System.out.println("✓ All movies now have showtimes including The Ritual");
        } catch (Exception e) {
            System.err.println("✗ Database reset failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 