package com.mycompany.cinemate.forms;

import com.mycompany.cinemate.dao.TicketTypeDAO;
import com.mycompany.cinemate.models.Movie;
import com.mycompany.cinemate.models.Showtime;
import com.mycompany.cinemate.models.Seat;
import com.mycompany.cinemate.models.TicketType;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingTicketsForm extends JFrame {
    private Movie selectedMovie;
    private Showtime selectedShowtime;
    private List<Seat> selectedSeats;
    private TicketTypeDAO ticketTypeDAO;
    
    private List<TicketType> availableTicketTypes;
    private Map<TicketType, JSpinner> ticketQuantitySpinners;
    private Map<TicketType, JLabel> ticketSubtotalLabels;
    
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    
    private JLabel totalPriceLabel;
    private JButton backButton;
    private JButton proceedButton;
    
    private BigDecimal totalPrice = BigDecimal.ZERO;
    
    public BookingTicketsForm(Movie movie, Showtime showtime, List<Seat> seats) {
        this.selectedMovie = movie;
        this.selectedShowtime = showtime;
        this.selectedSeats = seats;
        this.ticketTypeDAO = new TicketTypeDAO();
        this.ticketQuantitySpinners = new HashMap<>();
        this.ticketSubtotalLabels = new HashMap<>();
        
        initializeComponents();
        loadTicketTypes();
        setupLayout();
        setupEventListeners();
        
        setTitle("CineMate - Select Tickets & Enter Details");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Apply cinema theme
        getContentPane().setBackground(Color.BLACK);
    }
    
    private void initializeComponents() {
        // Customer details fields
        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 220, 0), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 220, 0), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        phoneField = new JTextField(20);
        phoneField.setFont(new Font("Arial", Font.PLAIN, 14));
        phoneField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 220, 0), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Total price label
        totalPriceLabel = new JLabel("TOTAL: ₱0.00");
        totalPriceLabel.setFont(new Font("Arial", Font.BOLD, 24));
        totalPriceLabel.setForeground(new Color(255, 220, 0));
        totalPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Buttons
        backButton = new JButton("BACK");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(153, 0, 0));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(120, 50));
        backButton.setFocusPainted(false);
        
        proceedButton = new JButton("PROCEED TO PAYMENT");
        proceedButton.setFont(new Font("Arial", Font.BOLD, 16));
        proceedButton.setBackground(new Color(255, 220, 0));
        proceedButton.setForeground(Color.BLACK);
        proceedButton.setPreferredSize(new Dimension(250, 50));
        proceedButton.setFocusPainted(false);
        proceedButton.setEnabled(false);
    }
    
    private void loadTicketTypes() {
        availableTicketTypes = ticketTypeDAO.getActiveTicketTypes();
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.BLACK);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("SELECT TICKET TYPES & ENTER DETAILS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(255, 220, 0));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Booking summary section
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JPanel summaryPanel = createBookingSummaryPanel();
        mainPanel.add(summaryPanel, gbc);
        
        // Ticket types section
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.weightx = 0.6;
        JPanel ticketTypesPanel = createTicketTypesPanel();
        mainPanel.add(ticketTypesPanel, gbc);
        
        // Customer details section
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.4;
        JPanel customerPanel = createCustomerDetailsPanel();
        mainPanel.add(customerPanel, gbc);
        
        // Total section
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.weightx = 1.0;
        JPanel totalPanel = createTotalPanel();
        mainPanel.add(totalPanel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Bottom buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        bottomPanel.add(backButton);
        bottomPanel.add(Box.createHorizontalStrut(50));
        bottomPanel.add(proceedButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createBookingSummaryPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255, 220, 0), 2),
            "Booking Summary", TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 16), new Color(255, 220, 0)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 15, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Movie info
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel movieLabel = new JLabel("Movie: " + selectedMovie.getTitle());
        movieLabel.setFont(new Font("Arial", Font.BOLD, 14));
        movieLabel.setForeground(Color.WHITE);
        panel.add(movieLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        JLabel dateLabel = new JLabel("Date: " + selectedShowtime.getFormattedShowDate());
        dateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        dateLabel.setForeground(Color.WHITE);
        panel.add(dateLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel timeLabel = new JLabel("Time: " + selectedShowtime.getFormattedShowTime());
        timeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timeLabel.setForeground(Color.WHITE);
        panel.add(timeLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        StringBuilder seatList = new StringBuilder("Seats: ");
        for (int i = 0; i < selectedSeats.size(); i++) {
            seatList.append(selectedSeats.get(i).getSeatLabel());
            if (i < selectedSeats.size() - 1) seatList.append(", ");
        }
        JLabel seatsLabel = new JLabel(seatList.toString());
        seatsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        seatsLabel.setForeground(Color.WHITE);
        panel.add(seatsLabel, gbc);
        
        return panel;
    }
    
    private JPanel createTicketTypesPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255, 220, 0), 2),
            "Select Ticket Types", TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 16), new Color(255, 220, 0)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;
        
        int row = 0;
        for (TicketType ticketType : availableTicketTypes) {
            // Ticket type name and price
            gbc.gridx = 0; gbc.gridy = row;
            JLabel typeLabel = new JLabel(ticketType.getTypeName());
            typeLabel.setFont(new Font("Arial", Font.BOLD, 14));
            typeLabel.setForeground(Color.WHITE);
            panel.add(typeLabel, gbc);
            
            gbc.gridx = 1; gbc.gridy = row;
            JLabel priceLabel = new JLabel(ticketType.getFormattedPrice());
            priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            priceLabel.setForeground(new Color(255, 220, 0));
            panel.add(priceLabel, gbc);
            
            // Quantity spinner
            gbc.gridx = 2; gbc.gridy = row;
            JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, selectedSeats.size(), 1));
            quantitySpinner.setPreferredSize(new Dimension(60, 30));
            quantitySpinner.addChangeListener(e -> updateTotalPrice());
            ticketQuantitySpinners.put(ticketType, quantitySpinner);
            panel.add(quantitySpinner, gbc);
            
            // Subtotal label
            gbc.gridx = 3; gbc.gridy = row;
            JLabel subtotalLabel = new JLabel("₱0.00");
            subtotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
            subtotalLabel.setForeground(new Color(255, 220, 0));
            ticketSubtotalLabels.put(ticketType, subtotalLabel);
            panel.add(subtotalLabel, gbc);
            
            row++;
        }
        
        return panel;
    }
    
    private JPanel createCustomerDetailsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255, 220, 0), 2),
            "Customer Details", TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 16), new Color(255, 220, 0)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Name *:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(Color.WHITE);
        panel.add(nameLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(nameField, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        JLabel emailLabel = new JLabel("Email *:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        emailLabel.setForeground(Color.WHITE);
        panel.add(emailLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(emailField, gbc);
        
        // Phone
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        JLabel phoneLabel = new JLabel("Phone (Optional):");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 14));
        phoneLabel.setForeground(Color.WHITE);
        panel.add(phoneLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(phoneField, gbc);
        
        return panel;
    }
    
    private JPanel createTotalPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 220, 0), 3),
            BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));
        panel.add(totalPriceLabel);
        return panel;
    }
    
    private void updateTotalPrice() {
        totalPrice = BigDecimal.ZERO;
        int totalTickets = 0;
        
        for (TicketType ticketType : availableTicketTypes) {
            JSpinner spinner = ticketQuantitySpinners.get(ticketType);
            int quantity = (Integer) spinner.getValue();
            
            BigDecimal subtotal = ticketType.getPrice().multiply(BigDecimal.valueOf(quantity));
            totalPrice = totalPrice.add(subtotal);
            totalTickets += quantity;
            
            // Update subtotal label
            JLabel subtotalLabel = ticketSubtotalLabels.get(ticketType);
            subtotalLabel.setText("₱" + subtotal.toString());
        }
        
        totalPriceLabel.setText("TOTAL: ₱" + totalPrice.toString());
        
        // Enable proceed button if we have tickets and customer details
        boolean hasTickets = totalTickets > 0 && totalTickets <= selectedSeats.size();
        boolean hasCustomerDetails = !nameField.getText().trim().isEmpty() && 
                                   !emailField.getText().trim().isEmpty();
        proceedButton.setEnabled(hasTickets && hasCustomerDetails);
    }
    
    private void setupEventListeners() {
        // Add document listeners to customer fields
        nameField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateTotalPrice(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateTotalPrice(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateTotalPrice(); }
        });
        
        emailField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateTotalPrice(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateTotalPrice(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateTotalPrice(); }
        });
        
        backButton.addActionListener(e -> {
            dispose();
            SeatsForm seatsForm = new SeatsForm(selectedMovie, selectedShowtime);
            seatsForm.setVisible(true);
        });
        
        proceedButton.addActionListener(e -> proceedToConfirmation());
    }
    
    private void proceedToConfirmation() {
        if (validateInput()) {
            // Create booking data
            Map<TicketType, Integer> ticketQuantities = new HashMap<>();
            for (TicketType ticketType : availableTicketTypes) {
                int quantity = (Integer) ticketQuantitySpinners.get(ticketType).getValue();
                if (quantity > 0) {
                    ticketQuantities.put(ticketType, quantity);
                }
            }
            
            String customerName = nameField.getText().trim();
            String customerEmail = emailField.getText().trim();
            String customerPhone = phoneField.getText().trim();
            
            dispose();
            PaymentForm paymentForm = new PaymentForm(
                selectedMovie, selectedShowtime, selectedSeats,
                ticketQuantities, customerName, customerEmail, customerPhone, totalPrice
            );
            paymentForm.setVisible(true);
        }
    }
    
    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return false;
        }
        
        if (emailField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your email.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            emailField.requestFocus();
            return false;
        }
        
        // Basic email validation
        if (!emailField.getText().contains("@")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            emailField.requestFocus();
            return false;
        }
        
        int totalTickets = 0;
        for (JSpinner spinner : ticketQuantitySpinners.values()) {
            totalTickets += (Integer) spinner.getValue();
        }
        
        if (totalTickets == 0) {
            JOptionPane.showMessageDialog(this, "Please select at least one ticket.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (totalTickets > selectedSeats.size()) {
            JOptionPane.showMessageDialog(this, 
                "You can only book " + selectedSeats.size() + " tickets for the selected seats.", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // For testing purposes - create dummy data
            Movie testMovie = new Movie();
            testMovie.setTitle("Test Movie");
            
            Showtime testShowtime = new Showtime();
            testShowtime.setId(1);
            
            java.util.List<Seat> testSeats = new java.util.ArrayList<>();
            // Add some test seats
            
            new BookingTicketsForm(testMovie, testShowtime, testSeats).setVisible(true);
        });
    }
} 