package com.mycompany.cinemate.forms;

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
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class PaymentForm extends JFrame {
    private Movie selectedMovie;
    private Showtime selectedShowtime;
    private List<Seat> selectedSeats;
    private Map<TicketType, Integer> ticketQuantities;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private BigDecimal totalPrice;
    
    // UI Components
    private JLabel totalAmountLabel;
    private JTextField cashAmountField;
    private JLabel changeAmountLabel;
    private JLabel statusLabel;
    private JButton backButton;
    private JButton confirmPaymentButton;
    
    private DecimalFormat currencyFormat;
    
    public PaymentForm(Movie movie, Showtime showtime, List<Seat> seats,
                      Map<TicketType, Integer> tickets, String name, String email, 
                      String phone, BigDecimal total) {
        this.selectedMovie = movie;
        this.selectedShowtime = showtime;
        this.selectedSeats = seats;
        this.ticketQuantities = tickets;
        this.customerName = name;
        this.customerEmail = email;
        this.customerPhone = phone;
        this.totalPrice = total;
        
        this.currencyFormat = new DecimalFormat("₱#,##0.00");
        
        initializeComponents();
        setupLayout();
        setupEventListeners();
        
        setTitle("CineMate - Payment");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Apply cinema theme
        getContentPane().setBackground(Color.BLACK);
    }
    
    private void initializeComponents() {
        // Total amount display
        totalAmountLabel = new JLabel();
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 24));
        totalAmountLabel.setForeground(new Color(255, 220, 0));
        totalAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updateTotalDisplay();
        
        // Cash amount input field
        cashAmountField = new JTextField(12);
        cashAmountField.setFont(new Font("Arial", Font.PLAIN, 16));
        cashAmountField.setHorizontalAlignment(SwingConstants.RIGHT);
        cashAmountField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 220, 0), 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        // Change amount display
        changeAmountLabel = new JLabel("₱0.00");
        changeAmountLabel.setFont(new Font("Arial", Font.BOLD, 20));
        changeAmountLabel.setForeground(new Color(0, 255, 0));
        changeAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Status label for messages
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        
        // Buttons
        backButton = new JButton("← Back to Tickets");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(139, 0, 0));
        backButton.setPreferredSize(new Dimension(180, 45));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(true);
        backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        backButton.setOpaque(true);
        
        confirmPaymentButton = new JButton("Confirm Payment");
        confirmPaymentButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmPaymentButton.setForeground(Color.WHITE);
        confirmPaymentButton.setBackground(new Color(0, 150, 0));
        confirmPaymentButton.setPreferredSize(new Dimension(180, 45));
        confirmPaymentButton.setFocusPainted(false);
        confirmPaymentButton.setBorderPainted(true);
        confirmPaymentButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        confirmPaymentButton.setOpaque(true);
        confirmPaymentButton.setEnabled(false);
        
        // Add hover effects
        addHoverEffect(backButton, new Color(139, 0, 0), new Color(180, 0, 0));
        addHoverEffect(confirmPaymentButton, new Color(0, 150, 0), new Color(0, 200, 0));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.BLACK);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("💰 PAYMENT 💰");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(255, 220, 0));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Main content panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Booking summary section
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JPanel summaryPanel = createBookingSummaryPanel();
        mainPanel.add(summaryPanel, gbc);
        
        // Payment section
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        JPanel paymentPanel = createPaymentPanel();
        mainPanel.add(paymentPanel, gbc);
        
        // Status message
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        mainPanel.add(statusLabel, gbc);
        
        // Button panel
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(backButton);
        buttonPanel.add(confirmPaymentButton);
        mainPanel.add(buttonPanel, gbc);
        
        // Wrap main panel in a scroll pane for smaller screens
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.getViewport().setBackground(Color.BLACK);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
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
        
        // Movie title
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel movieLabel = new JLabel("Movie:");
        movieLabel.setFont(new Font("Arial", Font.BOLD, 14));
        movieLabel.setForeground(Color.WHITE);
        panel.add(movieLabel, gbc);
        
        gbc.gridx = 1;
        JLabel movieValue = new JLabel(selectedMovie.getTitle());
        movieValue.setFont(new Font("Arial", Font.PLAIN, 14));
        movieValue.setForeground(new Color(255, 220, 0));
        panel.add(movieValue, gbc);
        
        // Showtime
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel timeLabel = new JLabel("Showtime:");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timeLabel.setForeground(Color.WHITE);
        panel.add(timeLabel, gbc);
        
        gbc.gridx = 1;
        String timeString = selectedShowtime.getFormattedShowDate() + " " + selectedShowtime.getFormattedShowTime();
        JLabel timeValue = new JLabel(timeString);
        timeValue.setFont(new Font("Arial", Font.PLAIN, 14));
        timeValue.setForeground(new Color(255, 220, 0));
        panel.add(timeValue, gbc);
        
        // Seats
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel seatsLabel = new JLabel("Seats:");
        seatsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        seatsLabel.setForeground(Color.WHITE);
        panel.add(seatsLabel, gbc);
        
        gbc.gridx = 1;
        StringBuilder seatList = new StringBuilder();
        for (int i = 0; i < selectedSeats.size(); i++) {
            seatList.append(selectedSeats.get(i).getSeatLabel());
            if (i < selectedSeats.size() - 1) seatList.append(", ");
        }
        JLabel seatsValue = new JLabel(seatList.toString());
        seatsValue.setFont(new Font("Arial", Font.PLAIN, 14));
        seatsValue.setForeground(new Color(255, 220, 0));
        panel.add(seatsValue, gbc);
        
        // Customer
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel customerLabel = new JLabel("Customer:");
        customerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        customerLabel.setForeground(Color.WHITE);
        panel.add(customerLabel, gbc);
        
        gbc.gridx = 1;
        JLabel customerValue = new JLabel(customerName);
        customerValue.setFont(new Font("Arial", Font.PLAIN, 14));
        customerValue.setForeground(new Color(255, 220, 0));
        panel.add(customerValue, gbc);
        
        return panel;
    }
    
    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255, 220, 0), 2),
            "Payment Details", TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 16), new Color(255, 220, 0)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Total amount due
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel totalLabel = new JLabel("Total Amount Due:");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setForeground(Color.WHITE);
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(totalLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(totalAmountLabel, gbc);
        
        // Cash amount input
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        JLabel cashLabel = new JLabel("Cash Amount (₱):");
        cashLabel.setFont(new Font("Arial", Font.BOLD, 16));
        cashLabel.setForeground(Color.WHITE);
        panel.add(cashLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(cashAmountField, gbc);
        
        // Change display
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        JLabel changeLabel = new JLabel("Change:");
        changeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        changeLabel.setForeground(Color.WHITE);
        panel.add(changeLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(changeAmountLabel, gbc);
        
        return panel;
    }
    
    private void updateTotalDisplay() {
        totalAmountLabel.setText(currencyFormat.format(totalPrice));
    }
    
    private void calculateChange() {
        try {
            statusLabel.setText(" ");
            statusLabel.setForeground(Color.RED);
            
            String cashText = cashAmountField.getText().trim();
            if (cashText.isEmpty()) {
                changeAmountLabel.setText("₱0.00");
                changeAmountLabel.setForeground(Color.WHITE);
                confirmPaymentButton.setEnabled(false);
                return;
            }
            
            // Remove currency symbols and commas if present
            cashText = cashText.replace("₱", "").replace(",", "").trim();
            
            BigDecimal cashAmount = new BigDecimal(cashText);
            BigDecimal change = cashAmount.subtract(totalPrice);
            
            if (cashAmount.compareTo(BigDecimal.ZERO) < 0) {
                statusLabel.setText("⚠️ Cash amount cannot be negative");
                changeAmountLabel.setText("₱0.00");
                changeAmountLabel.setForeground(Color.RED);
                confirmPaymentButton.setEnabled(false);
            } else if (change.compareTo(BigDecimal.ZERO) < 0) {
                // Insufficient payment
                statusLabel.setText("❌ Insufficient payment. Need " + 
                    currencyFormat.format(change.abs()) + " more");
                changeAmountLabel.setText(currencyFormat.format(change));
                changeAmountLabel.setForeground(Color.RED);
                confirmPaymentButton.setEnabled(false);
            } else if (change.compareTo(BigDecimal.ZERO) == 0) {
                // Exact payment
                statusLabel.setText("✅ Exact payment - Perfect!");
                statusLabel.setForeground(new Color(0, 255, 0));
                changeAmountLabel.setText("₱0.00");
                changeAmountLabel.setForeground(new Color(0, 255, 0));
                confirmPaymentButton.setEnabled(true);
            } else {
                // Overpayment - give change
                statusLabel.setText("✅ Payment accepted");
                statusLabel.setForeground(new Color(0, 255, 0));
                changeAmountLabel.setText(currencyFormat.format(change));
                changeAmountLabel.setForeground(new Color(0, 255, 0));
                confirmPaymentButton.setEnabled(true);
            }
            
        } catch (NumberFormatException e) {
            statusLabel.setText("❌ Please enter a valid amount");
            changeAmountLabel.setText("₱0.00");
            changeAmountLabel.setForeground(Color.RED);
            confirmPaymentButton.setEnabled(false);
        }
    }
    
    private void addHoverEffect(JButton button, Color normalColor, Color hoverColor) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(hoverColor);
                    button.repaint();
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normalColor);
                button.repaint();
            }
        });
    }
    
    private void setupEventListeners() {
        // Cash amount field listener for real-time change calculation
        cashAmountField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { calculateChange(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { calculateChange(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { calculateChange(); }
        });
        
        // Allow Enter key to proceed if payment is valid
        cashAmountField.addActionListener(e -> {
            if (confirmPaymentButton.isEnabled()) {
                proceedToConfirmation();
            }
        });
        
        backButton.addActionListener(e -> {
            dispose();
            BookingTicketsForm ticketsForm = new BookingTicketsForm(
                selectedMovie, selectedShowtime, selectedSeats);
            ticketsForm.setVisible(true);
        });
        
        confirmPaymentButton.addActionListener(e -> {
            if (validatePayment()) {
                proceedToConfirmation();
            }
        });
    }
    
    private boolean validatePayment() {
        try {
            String cashText = cashAmountField.getText().trim();
            if (cashText.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter the cash amount.", 
                    "Payment Required", 
                    JOptionPane.WARNING_MESSAGE);
                cashAmountField.requestFocus();
                return false;
            }
            
            cashText = cashText.replace("₱", "").replace(",", "").trim();
            BigDecimal cashAmount = new BigDecimal(cashText);
            
            if (cashAmount.compareTo(totalPrice) < 0) {
                JOptionPane.showMessageDialog(this, 
                    "Insufficient payment.\nTotal due: " + currencyFormat.format(totalPrice) + 
                    "\nCash provided: " + currencyFormat.format(cashAmount), 
                    "Insufficient Payment", 
                    JOptionPane.ERROR_MESSAGE);
                cashAmountField.requestFocus();
                return false;
            }
            
            // Show payment confirmation
            BigDecimal change = cashAmount.subtract(totalPrice);
            String message = "Payment Confirmation:\n\n" +
                           "Total Due: " + currencyFormat.format(totalPrice) + "\n" +
                           "Cash Received: " + currencyFormat.format(cashAmount) + "\n" +
                           "Change: " + currencyFormat.format(change) + "\n\n" +
                           "Proceed with this payment?";
            
            int result = JOptionPane.showConfirmDialog(this, 
                message, 
                "Confirm Payment", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            return result == JOptionPane.YES_OPTION;
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid cash amount.", 
                "Invalid Amount", 
                JOptionPane.ERROR_MESSAGE);
            cashAmountField.requestFocus();
            return false;
        }
    }
    
    private void proceedToConfirmation() {
        dispose();
        ConfirmationForm confirmationForm = new ConfirmationForm(
            selectedMovie, selectedShowtime, selectedSeats,
            ticketQuantities, customerName, customerEmail, customerPhone, totalPrice
        );
        confirmationForm.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // For testing purposes - create dummy data
            Movie testMovie = new Movie();
            testMovie.setTitle("Test Movie");
            
            Showtime testShowtime = new Showtime();
            testShowtime.setId(1);
            
            java.util.List<Seat> testSeats = new java.util.ArrayList<>();
            java.util.Map<TicketType, Integer> testTickets = new java.util.HashMap<>();
            
            new PaymentForm(testMovie, testShowtime, testSeats, testTickets, 
                           "Test Customer", "test@email.com", "123-456-7890", 
                           new BigDecimal("250.00")).setVisible(true);
        });
    }
} 