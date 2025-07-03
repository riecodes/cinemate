package com.mycompany.cinemate.forms;

import com.mycompany.cinemate.dao.SeatDAO;
import com.mycompany.cinemate.models.Movie;
import com.mycompany.cinemate.models.Showtime;
import com.mycompany.cinemate.models.Seat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SeatsForm extends JFrame {
    private static final int ROWS = 6;
    private static final int SEATS_PER_ROW = 8;
    private static final char FIRST_ROW = 'A';
    
    private Movie selectedMovie;
    private Showtime selectedShowtime;
    private SeatDAO seatDAO;
    
    private JButton[][] seatButtons;
    private List<Seat> availableSeats;
    private List<Seat> selectedSeats;
    private JLabel selectionInfoLabel;
    private JButton backButton;
    private JButton nextButton;
    
    public SeatsForm(Movie movie, Showtime showtime) {
        this.selectedMovie = movie;
        this.selectedShowtime = showtime;
        this.seatDAO = new SeatDAO();
        this.selectedSeats = new ArrayList<>();
        
        initializeComponents();
        setupLayout();
        loadSeats();
        setupEventListeners();
        
        setTitle("CineMate - Select Your Seats");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Apply cinema theme
        getContentPane().setBackground(new Color(153, 0, 0)); // Cinema red
    }
    
    private void initializeComponents() {
        seatButtons = new JButton[ROWS][SEATS_PER_ROW];
        
        // Initialize seat buttons
        for (int row = 0; row < ROWS; row++) {
            for (int seat = 0; seat < SEATS_PER_ROW; seat++) {
                seatButtons[row][seat] = new JButton();
                seatButtons[row][seat].setPreferredSize(new Dimension(50, 45));
                seatButtons[row][seat].setFont(new Font("Arial", Font.BOLD, 12));
                seatButtons[row][seat].setFocusPainted(false);
                seatButtons[row][seat].setBorder(BorderFactory.createRaisedBevelBorder());
                
                // Default styling (available seat)
                seatButtons[row][seat].setBackground(Color.BLACK);
                seatButtons[row][seat].setForeground(Color.WHITE);
                seatButtons[row][seat].setText("");
                
                // Add action listener for seat selection
                final int r = row;
                final int s = seat;
                seatButtons[row][seat].addActionListener(e -> toggleSeatSelection(r, s));
            }
        }
        
        selectionInfoLabel = new JLabel("SELECT YOUR SEATS");
        selectionInfoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        selectionInfoLabel.setForeground(Color.WHITE);
        selectionInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        backButton = new JButton("BACK");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(120, 50));
        backButton.setFocusPainted(false);
        
        nextButton = new JButton("NEXT");
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
        nextButton.setPreferredSize(new Dimension(120, 50));
        nextButton.setFocusPainted(false);
        nextButton.setEnabled(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Header with movie info
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(153, 0, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel movieInfoLabel = new JLabel("NEXT FORM CHAIRS TO PICK FOR RESERVATION");
        movieInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        movieInfoLabel.setForeground(Color.BLACK);
        headerPanel.add(movieInfoLabel, BorderLayout.WEST);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(153, 0, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // Screen panel
        JPanel screenPanel = createScreenPanel();
        mainPanel.add(screenPanel, BorderLayout.NORTH);
        
        // Seating area
        JPanel seatingPanel = createSeatingPanel();
        mainPanel.add(seatingPanel, BorderLayout.CENTER);
        
        // Selection info
        JPanel infoPanel = new JPanel(new FlowLayout());
        infoPanel.setBackground(new Color(153, 0, 0));
        infoPanel.add(selectionInfoLabel);
        mainPanel.add(infoPanel, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Bottom buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(new Color(153, 0, 0));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        bottomPanel.add(backButton);
        bottomPanel.add(Box.createHorizontalStrut(50));
        bottomPanel.add(nextButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createScreenPanel() {
        JPanel screenPanel = new JPanel(new FlowLayout());
        screenPanel.setBackground(new Color(153, 0, 0));
        screenPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        // Create screen marquee
        JPanel marqueePanel = new JPanel();
        marqueePanel.setBackground(Color.WHITE);
        marqueePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 220, 0), 8),
            BorderFactory.createEmptyBorder(20, 40, 20, 40)
        ));
        
        JLabel screenLabel = new JLabel("SCREEN");
        screenLabel.setFont(new Font("Arial", Font.BOLD, 36));
        screenLabel.setForeground(new Color(153, 0, 0));
        screenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        marqueePanel.add(screenLabel);
        screenPanel.add(marqueePanel);
        
        return screenPanel;
    }
    
    private JPanel createSeatingPanel() {
        JPanel seatingPanel = new JPanel(new GridBagLayout());
        seatingPanel.setBackground(new Color(153, 0, 0));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        for (int row = 0; row < ROWS; row++) {
            // Left row label
            gbc.gridx = 0;
            gbc.gridy = row;
            JLabel leftLabel = new JLabel(String.valueOf((char)(FIRST_ROW + row)));
            leftLabel.setFont(new Font("Arial", Font.BOLD, 24));
            leftLabel.setForeground(Color.WHITE);
            seatingPanel.add(leftLabel, gbc);
            
            // First 4 seats
            for (int seat = 0; seat < 4; seat++) {
                gbc.gridx = seat + 1;
                gbc.gridy = row;
                seatingPanel.add(seatButtons[row][seat], gbc);
            }
            
            // Center aisle gap
            gbc.gridx = 5;
            gbc.gridy = row;
            JLabel aisleLabel = new JLabel("    ");
            seatingPanel.add(aisleLabel, gbc);
            
            // Last 4 seats
            for (int seat = 4; seat < 8; seat++) {
                gbc.gridx = seat + 2;
                gbc.gridy = row;
                seatingPanel.add(seatButtons[row][seat], gbc);
            }
            
            // Right row label
            gbc.gridx = 10;
            gbc.gridy = row;
            JLabel rightLabel = new JLabel(String.valueOf((char)(FIRST_ROW + row)));
            rightLabel.setFont(new Font("Arial", Font.BOLD, 24));
            rightLabel.setForeground(Color.WHITE);
            seatingPanel.add(rightLabel, gbc);
        }
        
        return seatingPanel;
    }
    
    private void loadSeats() {
        // Get seats from database
        availableSeats = seatDAO.getSeatsByShowtimeId(selectedShowtime.getId());
        
        // Update seat button states
        for (int row = 0; row < ROWS; row++) {
            for (int seat = 0; seat < SEATS_PER_ROW; seat++) {
                char seatRow = (char)(FIRST_ROW + row);
                int seatNumber = seat + 1;
                
                // Find corresponding seat in database
                Seat dbSeat = findSeat(seatRow, seatNumber);
                
                if (dbSeat != null && dbSeat.isBooked()) {
                    // Seat is already booked
                    seatButtons[row][seat].setBackground(Color.GRAY);
                    seatButtons[row][seat].setText("X");
                    seatButtons[row][seat].setEnabled(false);
                } else {
                    // Seat is available
                    seatButtons[row][seat].setBackground(Color.BLACK);
                    seatButtons[row][seat].setText("");
                    seatButtons[row][seat].setEnabled(true);
                }
            }
        }
    }
    
    private Seat findSeat(char seatRow, int seatNumber) {
        return availableSeats.stream()
            .filter(seat -> seat.getSeatRow() == seatRow && seat.getSeatNumber() == seatNumber)
            .findFirst()
            .orElse(null);
    }
    
    private void toggleSeatSelection(int row, int seat) {
        JButton button = seatButtons[row][seat];
        char seatRow = (char)(FIRST_ROW + row);
        int seatNumber = seat + 1;
        
        // Find the seat object
        Seat seatObj = findSeat(seatRow, seatNumber);
        if (seatObj == null || seatObj.isBooked()) {
            return; // Can't select booked seats
        }
        
        if (selectedSeats.contains(seatObj)) {
            // Deselect seat
            selectedSeats.remove(seatObj);
            button.setBackground(Color.BLACK);
            button.setText("");
        } else {
            // Select seat
            selectedSeats.add(seatObj);
            button.setBackground(new Color(255, 220, 0)); // Gold color for selected
            button.setForeground(Color.BLACK);
            button.setText(String.valueOf(selectedSeats.size()));
        }
        
        updateSelectionInfo();
        updateNextButton();
    }
    
    private void updateSelectionInfo() {
        if (selectedSeats.isEmpty()) {
            selectionInfoLabel.setText("SELECT YOUR SEATS");
        } else {
            StringBuilder info = new StringBuilder("SELECTED SEATS: ");
            for (int i = 0; i < selectedSeats.size(); i++) {
                Seat seat = selectedSeats.get(i);
                info.append(seat.getSeatLabel());
                if (i < selectedSeats.size() - 1) {
                    info.append(", ");
                }
            }
            selectionInfoLabel.setText(info.toString());
        }
    }
    
    private void updateNextButton() {
        nextButton.setEnabled(!selectedSeats.isEmpty());
    }
    
    private void setupEventListeners() {
        backButton.addActionListener(e -> {
            dispose();
            new ReservationForm(selectedMovie).setVisible(true);
        });
        
        nextButton.addActionListener(e -> proceedToTicketSelection());
    }
    
    private void proceedToTicketSelection() {
        if (!selectedSeats.isEmpty()) {
            dispose();
            BookingTicketsForm ticketsForm = new BookingTicketsForm(selectedMovie, selectedShowtime, selectedSeats);
            ticketsForm.setVisible(true);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // For testing purposes - create dummy data
            Movie testMovie = new Movie();
            testMovie.setTitle("Test Movie");
            
            Showtime testShowtime = new Showtime();
            testShowtime.setId(1);
            
            new SeatsForm(testMovie, testShowtime).setVisible(true);
        });
    }
} 