package com.mycompany.cinemate.forms;

import com.mycompany.cinemate.dao.MovieDAO;
import com.mycompany.cinemate.models.Movie;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

public class TicketsForm extends JFrame {
    private JPanel ticketsPanel;
    private JLabel logoLabel;
    private JButton backButton;
    private MovieDAO movieDAO;
    private List<Movie> movies;
    
    public TicketsForm() {
        movieDAO = new MovieDAO();
        loadMoviesFromDatabase();
        initializeComponents();
        setupLayout();
        setFrameProperties();
    }
    
    // Public method to refresh the tickets display
    public void refreshTickets() {
        loadMoviesFromDatabase();
        
        // Remove the old tickets panel from the container
        getContentPane().remove(ticketsPanel);
        
        // Create new tickets panel
        createTicketsPanel();
        
        // Add it back to the layout
        getContentPane().add(ticketsPanel, BorderLayout.CENTER);
        
        // Refresh the display
        revalidate();
        repaint();
    }
    
    private void loadMoviesFromDatabase() {
        try {
            movies = movieDAO.getAllActiveMovies();
            if (movies.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "No active movies available for booking.", 
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
        
        // Create tickets grid panel
        createTicketsPanel();
        
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
    
    private void createTicketsPanel() {
        // Calculate grid dimensions based on number of movies
        int movieCount = movies.size();
        int cols = Math.min(5, movieCount); // Max 5 columns
        int rows = (int) Math.ceil((double) movieCount / cols);
        
        ticketsPanel = new JPanel(new GridLayout(rows, cols, 18, 20));
        ticketsPanel.setBackground(new Color(139, 0, 0)); // Cinema red background
        ticketsPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Add movie cards
        for (Movie movie : movies) {
            JPanel movieCard = createMovieCard(movie);
            ticketsPanel.add(movieCard);
        }
    }
    
    private JPanel createMovieCard(Movie movie) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(new Color(139, 0, 0));
        card.setPreferredSize(new Dimension(220, 320));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 1), // Gold border
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        
        // Movie poster with frame effect
        JPanel posterPanel = new JPanel(new BorderLayout());
        posterPanel.setBackground(new Color(139, 0, 0));
        posterPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2), // Black border
            BorderFactory.createLineBorder(new Color(139, 0, 0), 2)
        ));
        
        JLabel posterLabel = new JLabel();
        posterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        posterLabel.setVerticalAlignment(SwingConstants.CENTER);
        posterLabel.setBackground(new Color(139, 0, 0));
        posterLabel.setOpaque(true);
        
        try {
            URL posterUrl = getClass().getResource(movie.getPosterPath());
            if (posterUrl != null) {
                ImageIcon posterIcon = new ImageIcon(posterUrl);
                Image img = posterIcon.getImage();
                Image scaledImg = img.getScaledInstance(185, 230, Image.SCALE_SMOOTH);
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
        
        // Book Now button
        JButton bookButton = createBookButton(movie);
        
        // Bottom panel for title and button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(139, 0, 0));
        bottomPanel.add(titleLabel, BorderLayout.CENTER);
        bottomPanel.add(bookButton, BorderLayout.SOUTH);
        
        card.add(posterPanel, BorderLayout.CENTER);
        card.add(bottomPanel, BorderLayout.SOUTH);
        
        return card;
    }
    
    private JButton createBookButton(Movie movie) {
        JButton button = new JButton("BOOK NOW");
        button.setFont(new Font("Arial", Font.BOLD, 10));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setPreferredSize(new Dimension(150, 32));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2), // Gold border
            BorderFactory.createLineBorder(Color.BLACK, 1)
        ));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 215, 0)); // Gold background
                button.setForeground(Color.BLACK);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE, 2),
                    BorderFactory.createLineBorder(new Color(255, 215, 0), 1)
                ));
                button.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
                    BorderFactory.createLineBorder(Color.BLACK, 1)
                ));
                button.repaint();
            }
        });
        
        // Action listener for booking
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // For now, show a booking dialog placeholder
                showBookingDialog(movie);
            }
        });
        
        return button;
    }
    
    private void showBookingDialog(Movie movie) {
        // Open reservation form with the selected movie
        dispose(); // Close tickets form
        ReservationForm reservationForm = new ReservationForm(movie); // Pass the selected movie
        reservationForm.setVisible(true);
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
                dispose();
            }
        });
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(139, 0, 0)); // Cinema red background
        
        // Top panel with title and logo
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(139, 0, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 15, 40));
        
        // Title section
        JPanel titleSection = new JPanel(new BorderLayout());
        titleSection.setBackground(new Color(139, 0, 0));
        
        JLabel titleLabel = new JLabel("TICKETS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("//TICKETS FORM (RESERVATION OR BOOKING OF TICKET)");
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        subtitleLabel.setForeground(Color.WHITE);
        
        titleSection.add(titleLabel, BorderLayout.NORTH);
        titleSection.add(subtitleLabel, BorderLayout.SOUTH);
        
        // Logo section with background
        JPanel logoSection = new JPanel(new BorderLayout());
        logoSection.setBackground(new Color(180, 0, 0, 100)); // Semi-transparent background
        logoSection.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        logoSection.setPreferredSize(new Dimension(270, 140));
        logoSection.add(logoLabel, BorderLayout.CENTER);
        
        topPanel.add(titleSection, BorderLayout.WEST);
        topPanel.add(logoSection, BorderLayout.EAST);
        
        // Bottom panel with back button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 40, 20));
        bottomPanel.setBackground(new Color(139, 0, 0));
        bottomPanel.add(backButton);
        
        add(topPanel, BorderLayout.NORTH);
        add(ticketsPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setFrameProperties() {
        setTitle("The CineMate - Tickets & Reservations");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1600, 1000);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(1200, 800));
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window on open
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TicketsForm().setVisible(true);
            }
        });
    }
} 