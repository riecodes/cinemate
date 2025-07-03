package com.mycompany.cinemate.forms;

import com.mycompany.cinemate.dao.MovieDAO;
import com.mycompany.cinemate.models.Movie;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

public class MoviesForm extends JFrame {
    private JPanel moviesPanel;
    private JLabel logoLabel;
    private JButton backButton;
    private MovieDAO movieDAO;
    private List<Movie> movies;
    
    public MoviesForm() {
        movieDAO = new MovieDAO();
        loadMoviesFromDatabase();
        initializeComponents();
        setupLayout();
        setFrameProperties();
    }
    
    // Public method to refresh the movies display
    public void refreshMovies() {
        loadMoviesFromDatabase();
        
        // Remove the old movies panel from the container
        getContentPane().remove(moviesPanel);
        
        // Create new movies panel
        createMoviesPanel();
        
        // Add it back to the layout
        getContentPane().add(moviesPanel, BorderLayout.CENTER);
        
        // Refresh the display
        revalidate();
        repaint();
    }
    
    private void loadMoviesFromDatabase() {
        try {
            movies = movieDAO.getAllActiveMovies();
            if (movies.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "No active movies found in database.", 
                    "No Movies", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error loading movies from database: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            movies = new java.util.ArrayList<>(); // Initialize empty list
        }
    }
    
    private void initializeComponents() {
        // Create logo overlay
        createLogoOverlay();
        
        // Create movies grid panel
        createMoviesPanel();
        
        // Create back button
        createBackButton();
    }
    
    private void createLogoOverlay() {
        logoLabel = new JLabel();
        try {
            URL logoUrl = getClass().getResource("/assets/logo.png");
            if (logoUrl != null) {
                ImageIcon logoIcon = new ImageIcon(logoUrl);
                // Scale the logo for upper right placement
                Image img = logoIcon.getImage();
                Image scaledImg = img.getScaledInstance(250, 125, Image.SCALE_SMOOTH);
                logoLabel.setIcon(new ImageIcon(scaledImg));
            } else {
                logoLabel.setText("THE CINEMATE");
                logoLabel.setFont(new Font("Arial", Font.BOLD, 16));
                logoLabel.setForeground(Color.WHITE);
            }
        } catch (Exception e) {
            logoLabel.setText("THE CINEMATE");
            logoLabel.setFont(new Font("Arial", Font.BOLD, 16));
            logoLabel.setForeground(Color.WHITE);
        }
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setVerticalAlignment(SwingConstants.CENTER);
    }
    
    private void createMoviesPanel() {
        // Calculate grid dimensions based on number of movies
        int movieCount = movies.size();
        int cols = Math.min(5, movieCount); // Max 5 columns
        int rows = (int) Math.ceil((double) movieCount / cols);
        
        moviesPanel = new JPanel(new GridLayout(rows, cols, 18, 20));
        moviesPanel.setBackground(Color.BLACK);
        moviesPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Add movie cards
        for (Movie movie : movies) {
            JPanel movieCard = createMovieCard(movie);
            moviesPanel.add(movieCard);
        }
    }
    
    private JPanel createMovieCard(Movie movie) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.BLACK);
        card.setPreferredSize(new Dimension(220, 320)); // Increased size to prevent cut-off
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 1), // Gold border
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        
        // Movie poster with frame effect
        JPanel posterPanel = new JPanel(new BorderLayout());
        posterPanel.setBackground(Color.BLACK);
        posterPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(139, 0, 0), 2), // Red border
            BorderFactory.createLineBorder(Color.BLACK, 2)
        ));
        
        JLabel posterLabel = new JLabel();
        posterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        posterLabel.setVerticalAlignment(SwingConstants.CENTER);
        posterLabel.setBackground(Color.BLACK);
        posterLabel.setOpaque(true);
        
        try {
            URL posterUrl = getClass().getResource(movie.getPosterPath());
            if (posterUrl != null) {
                ImageIcon posterIcon = new ImageIcon(posterUrl);
                Image img = posterIcon.getImage();
                Image scaledImg = img.getScaledInstance(185, 230, Image.SCALE_SMOOTH); // Increased poster size
                posterLabel.setIcon(new ImageIcon(scaledImg));
            } else {
                posterLabel.setText("NO IMAGE");
                posterLabel.setForeground(new Color(255, 215, 0));
                posterLabel.setFont(new Font("Arial", Font.BOLD, 12));
            }
        } catch (Exception e) {
            posterLabel.setText("NO IMAGE");
            posterLabel.setForeground(new Color(255, 215, 0));
            posterLabel.setFont(new Font("Arial", Font.BOLD, 12));
        }
        
        posterPanel.add(posterLabel, BorderLayout.CENTER);
        
        // Movie title with cinema styling
        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'>" + movie.getTitle() + "</div></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 11));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(6, 3, 4, 3));
        
        // See Details button
        JButton detailsButton = createDetailsButton(movie);
        
        // Bottom panel for title and button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.add(titleLabel, BorderLayout.CENTER);
        bottomPanel.add(detailsButton, BorderLayout.SOUTH);
        
        card.add(posterPanel, BorderLayout.CENTER);
        card.add(bottomPanel, BorderLayout.SOUTH);
        
        return card;
    }
    
    private JButton createDetailsButton(Movie movie) {
        JButton button = new JButton("SEE DETAILS");
        button.setFont(new Font("Arial", Font.BOLD, 10));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(139, 0, 0)); // Dark red
        button.setPreferredSize(new Dimension(150, 32)); // Increased button size
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2), // Gold border
            BorderFactory.createLineBorder(new Color(139, 0, 0), 1)
        ));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        
        // Hover effect with animation-like feel
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(200, 0, 0));
                button.setForeground(new Color(255, 215, 0)); // Gold text on hover
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE, 2),
                    BorderFactory.createLineBorder(new Color(200, 0, 0), 1)
                ));
                button.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(139, 0, 0));
                button.setForeground(Color.WHITE);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
                    BorderFactory.createLineBorder(new Color(139, 0, 0), 1)
                ));
                button.repaint();
            }
        });
        
        // Action listener
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the movie details form with the complete Movie object
                new MovieDetailsForm(movie).setVisible(true);
            }
        });
        
        return button;
    }
    
    private void createBackButton() {
        backButton = new JButton("BACK");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setForeground(Color.BLACK);
        backButton.setBackground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(true);
        backButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2), // Gold border
            BorderFactory.createEmptyBorder(3, 8, 3, 8)
        ));
        backButton.setOpaque(true);
        backButton.setContentAreaFilled(true);
        
        // Hover effect
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(255, 215, 0)); // Gold background
                backButton.setForeground(Color.BLACK);
                backButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE, 2),
                    BorderFactory.createEmptyBorder(3, 8, 3, 8)
                ));
                backButton.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(Color.WHITE);
                backButton.setForeground(Color.BLACK);
                backButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
                    BorderFactory.createEmptyBorder(3, 8, 3, 8)
                ));
                backButton.repaint();
            }
        });
        
        // Action listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close this window
            }
        });
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);
        
        // Create top panel with title and logo
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 15, 40));
        
        // Left side - Title section
        JPanel titleSection = new JPanel();
        titleSection.setLayout(new BoxLayout(titleSection, BoxLayout.Y_AXIS));
        titleSection.setBackground(Color.BLACK);
        
        JLabel titleLabel = new JLabel("//MOVIES FORM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(255, 215, 0)); // Gold color
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel availableLabel = new JLabel("AVAILABLE MOVIES");
        availableLabel.setFont(new Font("Arial", Font.BOLD, 42));
        availableLabel.setForeground(Color.WHITE);
        availableLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Add decorative line
        JLabel starsLabel = new JLabel("- - - - -");
        starsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        starsLabel.setForeground(new Color(255, 215, 0)); // Gold color
        starsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        titleSection.add(titleLabel);
        titleSection.add(Box.createRigidArea(new Dimension(0, 8)));
        titleSection.add(availableLabel);
        titleSection.add(Box.createRigidArea(new Dimension(0, 5)));
        titleSection.add(starsLabel);
        
        // Right side - Logo with background effect
        JPanel logoSection = new JPanel();
        logoSection.setLayout(new BorderLayout());
        logoSection.setBackground(new Color(139, 0, 0, 80)); // Semi-transparent red background
        logoSection.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2), // Gold border
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        logoSection.setPreferredSize(new Dimension(280, 140));
        logoSection.add(logoLabel, BorderLayout.CENTER);
        
        topPanel.add(titleSection, BorderLayout.WEST);
        topPanel.add(logoSection, BorderLayout.EAST);
        
        // Create center panel with improved movie grid
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.BLACK);
        
        // Add decorative marquee-style border
        JPanel marqueePanel = new JPanel(new BorderLayout());
        marqueePanel.setBackground(Color.BLACK);
        marqueePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 3), // Gold outer border
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 5),
                BorderFactory.createLineBorder(new Color(139, 0, 0), 2) // Red inner border
            )
        ));
        marqueePanel.add(moviesPanel, BorderLayout.CENTER);
        
        centerPanel.add(marqueePanel, BorderLayout.CENTER);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
        
        // Bottom panel with enhanced back button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 40));
        
        // Add cinema info on the left
        JLabel cinemaInfo = new JLabel("THE CINEMATE EXPERIENCE");
        cinemaInfo.setFont(new Font("Arial", Font.BOLD, 14));
        cinemaInfo.setForeground(new Color(255, 215, 0));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(backButton);
        
        bottomPanel.add(cinemaInfo, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setFrameProperties() {
        setTitle("The CineMate - Available Movies");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1600, 1000);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(1200, 800));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MoviesForm().setVisible(true);
            }
        });
    }
} 