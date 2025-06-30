package com.mycompany.cinemate;

import com.mycompany.cinemate.forms.MainMenuForm;
import com.mycompany.cinemate.database.DatabaseConfig;
import com.mycompany.cinemate.database.DatabaseSchema;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JOptionPane;

public class Cinemate {

    public static void main(String[] args) {
        // Set look and feel to Metal (cross-platform) for better custom styling
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Initialize database on application start
        initializeDatabase();
        
        // Launch the GUI application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenuForm().setVisible(true);
            }
        });
    }
    
    private static void initializeDatabase() {
        try {
            System.out.println("Initializing CineMate database...");
            
            // Test database connection
            if (DatabaseConfig.testConnection()) {
                System.out.println("Database connection successful!");
                
                // Reset and initialize database schema and data
                DatabaseSchema.resetDatabase();
                System.out.println("Database initialization complete!");
                
            } else {
                System.err.println("Failed to connect to database!");
                showDatabaseError();
            }
            
        } catch (Exception e) {
            System.err.println("Database initialization error: " + e.getMessage());
            e.printStackTrace();
            showDatabaseError();
        }
    }
    
    private static void showDatabaseError() {
        SwingUtilities.invokeLater(() -> {
            String message = "Database Connection Failed!\n\n" +
                           "Please ensure:\n" +
                           "1. XAMPP MySQL is running\n" +
                           "2. MySQL server is accessible on localhost:3306\n" +
                           "3. Database user 'root' has appropriate permissions\n\n" +
                           "The application will continue with limited functionality.";
            
            JOptionPane.showMessageDialog(null, message, "Database Error", JOptionPane.WARNING_MESSAGE);
        });
    }
}
