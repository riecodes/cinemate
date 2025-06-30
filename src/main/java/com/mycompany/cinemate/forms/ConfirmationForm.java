package com.mycompany.cinemate.forms;

import com.mycompany.cinemate.dao.BookingDAO;
import com.mycompany.cinemate.database.DatabaseConfig;
import com.mycompany.cinemate.models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ConfirmationForm extends JFrame {
    private Movie selectedMovie;
    private Showtime selectedShowtime;
    private List<Seat> selectedSeats;
    private Map<TicketType, Integer> ticketQuantities;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private BigDecimal totalPrice;
    private String bookingCode;
    
    private BookingDAO bookingDAO;
    
    private JTable detailTable;
    private JLabel totalLabel;
    private JButton cancelButton;
    private JButton printReceiptButton;
    
    public ConfirmationForm(Movie movie, Showtime showtime, List<Seat> seats,
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
        this.bookingDAO = new BookingDAO();
        
        generateBookingCode();
        initializeComponents();
        setupLayout();
        setupEventListeners();
        populateConfirmationDetails();
        
        setTitle("CineMate - Booking Confirmation");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Apply cinema theme
        getContentPane().setBackground(Color.WHITE);
    }
    
    private void generateBookingCode() {
        // Generate a random booking code
        Random random = new Random();
        bookingCode = String.format("%09d", random.nextInt(1000000000));
    }
    
    private void initializeComponents() {
        // Detail confirmation table
        String[] columnNames = {"MOVIE TITLE", "COST", "QUANTITY", "SEATS", "TIME", "SUBTOTAL"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        detailTable = new JTable(tableModel);
        detailTable.setFont(new Font("Arial", Font.PLAIN, 14));
        detailTable.setRowHeight(40);
        detailTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        detailTable.getTableHeader().setBackground(new Color(139, 0, 0));
        detailTable.getTableHeader().setForeground(Color.WHITE);
        detailTable.setGridColor(Color.BLACK);
        detailTable.setShowGrid(true);
        
        // Center align cell content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < detailTable.getColumnCount(); i++) {
            detailTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Note: Customer details will be created dynamically in the layout
        
        // Total label
        totalLabel = new JLabel("TOTAL: ₱" + totalPrice.toString());
        totalLabel.setFont(new Font("Arial", Font.BOLD, 36));
        totalLabel.setForeground(Color.BLACK);
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        // Buttons
        cancelButton = new JButton("CANCEL ORDER");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));
        cancelButton.setBackground(new Color(139, 0, 0));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(200, 60));
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(BorderFactory.createRaisedBevelBorder());
        
        printReceiptButton = new JButton("PRINT RECEIPT");
        printReceiptButton.setFont(new Font("Arial", Font.BOLD, 18));
        printReceiptButton.setBackground(new Color(139, 0, 0));
        printReceiptButton.setForeground(Color.WHITE);
        printReceiptButton.setPreferredSize(new Dimension(200, 60));
        printReceiptButton.setFocusPainted(false);
        printReceiptButton.setBorder(BorderFactory.createRaisedBevelBorder());
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Header section
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));
        
        // Logo section (upper right)
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoPanel.setBackground(Color.WHITE);
        
        JLabel logoLabel = new JLabel();
        try {
            java.net.URL logoUrl = getClass().getResource("/assets/logo.png");
            if (logoUrl != null) {
                ImageIcon logoIcon = new ImageIcon(logoUrl);
                Image img = logoIcon.getImage();
                Image scaledImg = img.getScaledInstance(200, 100, Image.SCALE_SMOOTH);
                logoLabel.setIcon(new ImageIcon(scaledImg));
            } else {
                logoLabel.setText("THE CINEMATE");
                logoLabel.setFont(new Font("Arial", Font.BOLD, 20));
                logoLabel.setForeground(new Color(139, 0, 0));
            }
        } catch (Exception e) {
            logoLabel.setText("THE CINEMATE");
            logoLabel.setFont(new Font("Arial", Font.BOLD, 20));
            logoLabel.setForeground(new Color(139, 0, 0));
        }
        logoPanel.add(logoLabel);
        
        // Title section
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        
        JLabel confirmationTitle = new JLabel("CONFIRMATION FORM");
        confirmationTitle.setFont(new Font("Arial", Font.BOLD, 24));
        confirmationTitle.setForeground(Color.BLACK);
        confirmationTitle.setHorizontalAlignment(SwingConstants.LEFT);
        titlePanel.add(confirmationTitle, BorderLayout.WEST);
        
        JLabel detailTitle = new JLabel("DETAIL CONFIRMATION");
        detailTitle.setFont(new Font("Arial", Font.BOLD, 28));
        detailTitle.setForeground(Color.BLACK);
        detailTitle.setHorizontalAlignment(SwingConstants.LEFT);
        detailTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        headerPanel.add(logoPanel, BorderLayout.EAST);
        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(detailTitle, BorderLayout.SOUTH);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        // Detail table section
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        JScrollPane tableScrollPane = new JScrollPane(detailTable);
        tableScrollPane.setPreferredSize(new Dimension(0, 150));
        tableScrollPane.setBorder(null);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        
        // Total section
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBackground(Color.WHITE);
        totalPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 50));
        totalPanel.add(totalLabel);
        
        // Personal details section - basic design
        JPanel personalPanel = new JPanel(new GridBagLayout());
        personalPanel.setBackground(Color.WHITE);
        personalPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Title
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel personalTitle = new JLabel("PERSONAL DETAILS:");
        personalTitle.setFont(new Font("Arial", Font.BOLD, 24));
        personalTitle.setForeground(new Color(139, 0, 0));
        personalPanel.add(personalTitle, gbc);
        
        // Name
        gbc.gridwidth = 1; gbc.gridy = 1;
        gbc.gridx = 0; 
        JLabel nameLabel = new JLabel("NAME:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(Color.BLACK);
        personalPanel.add(nameLabel, gbc);
        
        gbc.gridx = 1;
        JLabel nameValue = new JLabel(customerName);
        nameValue.setFont(new Font("Arial", Font.PLAIN, 16));
        nameValue.setForeground(Color.BLACK);
        personalPanel.add(nameValue, gbc);
        
        // Email
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel emailLabel = new JLabel("EMAIL:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 16));
        emailLabel.setForeground(Color.BLACK);
        personalPanel.add(emailLabel, gbc);
        
        gbc.gridx = 1;
        JLabel emailValue = new JLabel(customerEmail);
        emailValue.setFont(new Font("Arial", Font.PLAIN, 16));
        emailValue.setForeground(Color.BLACK);
        personalPanel.add(emailValue, gbc);
        
        // Phone
        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel phoneLabel = new JLabel("PHONE:");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 16));
        phoneLabel.setForeground(Color.BLACK);
        personalPanel.add(phoneLabel, gbc);
        
        gbc.gridx = 1;
        String phoneDisplay = (customerPhone == null || customerPhone.trim().isEmpty()) ? "Not provided" : customerPhone;
        JLabel phoneValue = new JLabel(phoneDisplay);
        phoneValue.setFont(new Font("Arial", Font.PLAIN, 16));
        phoneValue.setForeground(Color.BLACK);
        personalPanel.add(phoneValue, gbc);
        
        // Layout main sections
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setBackground(Color.WHITE);
        topSection.add(tablePanel, BorderLayout.CENTER);
        topSection.add(totalPanel, BorderLayout.SOUTH);
        
        mainPanel.add(topSection, BorderLayout.NORTH);
        mainPanel.add(personalPanel, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Bottom section with buttons only
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 30));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
        bottomPanel.add(cancelButton);
        bottomPanel.add(printReceiptButton);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void populateConfirmationDetails() {
        DefaultTableModel model = (DefaultTableModel) detailTable.getModel();
        
        // Create seat list string
        StringBuilder seatList = new StringBuilder();
        for (int i = 0; i < selectedSeats.size(); i++) {
            seatList.append(selectedSeats.get(i).getSeatLabel());
            if (i < selectedSeats.size() - 1) seatList.append(", ");
        }
        
        // Create time string
        String timeString = selectedShowtime.getFormattedShowDate() + " " + selectedShowtime.getFormattedShowTime();
        
        // Add rows for each ticket type
        for (Map.Entry<TicketType, Integer> entry : ticketQuantities.entrySet()) {
            TicketType ticketType = entry.getKey();
            Integer quantity = entry.getValue();
            
            if (quantity > 0) {
                BigDecimal subtotal = ticketType.getPrice().multiply(BigDecimal.valueOf(quantity));
                
                Object[] rowData = {
                    selectedMovie.getTitle(),
                    ticketType.getFormattedPrice(),
                    quantity.toString(),
                    seatList.toString(),
                    timeString,
                    "₱" + subtotal.toString()
                };
                
                model.addRow(rowData);
            }
        }
    }
    
    private void setupEventListeners() {
        cancelButton.addActionListener(_ -> {
            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to cancel this booking?",
                "Cancel Booking",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (choice == JOptionPane.YES_OPTION) {
                dispose(); // Just close the confirmation form
            }
        });
        
        printReceiptButton.addActionListener(_ -> {
            if (saveBookingToDatabase()) {
                printReceipt();
                
                // Create seat list for display
                StringBuilder seatList = new StringBuilder();
                for (int i = 0; i < selectedSeats.size(); i++) {
                    seatList.append(selectedSeats.get(i).getSeatLabel());
                    if (i < selectedSeats.size() - 1) seatList.append(", ");
                }
                
                // Show detailed success message with actual database booking ID
                JOptionPane.showMessageDialog(
                    this,
                    "🎉 BOOKING SUCCESSFUL! 🎉\n\n" +
                    "Booking ID: " + bookingCode + "\n" +
                    "Customer: " + customerName + "\n" +
                    "Movie: " + selectedMovie.getTitle() + "\n" +
                    "Seats: " + seatList.toString() + "\n" +
                    "Total: ₱" + totalPrice + "\n\n" +
                    "✅ Receipt has been printed successfully.\n" +
                    "📧 Confirmation details saved to database.\n" +
                    "🎬 Please keep your receipt for entry.\n\n" +
                    "Thank you for choosing THE CINEMATE!",
                    "Booking Confirmed - ID: " + bookingCode,
                    JOptionPane.INFORMATION_MESSAGE
                );
                
                dispose(); // Close the confirmation form after successful booking
            }
        });
    }
    
    private boolean saveBookingToDatabase() {
        try {
            // Create booking object
            Booking booking = new Booking();
            booking.setCustomerName(customerName);
            booking.setCustomerEmail(customerEmail);
            booking.setCustomerPhone(customerPhone);
            booking.setMovieId(selectedMovie.getId());
            booking.setShowtimeId(selectedShowtime.getId());
            booking.setBookingDate(LocalDate.now());
            booking.setTotalPrice(totalPrice);
            booking.setBookingStatus(Booking.BookingStatus.CONFIRMED);
            
            // Create seat list
            StringBuilder seatList = new StringBuilder();
            for (int i = 0; i < selectedSeats.size(); i++) {
                seatList.append(selectedSeats.get(i).getSeatLabel());
                if (i < selectedSeats.size() - 1) seatList.append(",");
            }
            booking.setSeats(seatList.toString());
            
            // Save booking and get the generated ID
            int bookingId = bookingDAO.addBooking(booking);
            if (bookingId > 0) {
                // Update booking code to use the actual database ID
                bookingCode = String.valueOf(bookingId);
                
                // Mark seats as booked in database
                markSeatsAsBooked();
                
                return true;
            } else {
                throw new RuntimeException("Failed to save booking to database");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Error saving booking: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }
    
    private void markSeatsAsBooked() {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String updateSeatSQL = "UPDATE seats SET is_booked = true WHERE showtime_id = ? AND seat_row = ? AND seat_number = ?";
            
            try (PreparedStatement pstmt = conn.prepareStatement(updateSeatSQL)) {
                for (Seat seat : selectedSeats) {
                    // Parse seat label (e.g., "A1" -> row="A", number=1)
                    String seatLabel = seat.getSeatLabel();
                    char seatRow = seatLabel.charAt(0);
                    int seatNumber = Integer.parseInt(seatLabel.substring(1));
                    
                    pstmt.setInt(1, selectedShowtime.getId());
                    pstmt.setString(2, String.valueOf(seatRow));
                    pstmt.setInt(3, seatNumber);
                    pstmt.executeUpdate();
                }
            }
            
            // Also update available seats count in showtimes table
            String updateShowtimeSQL = "UPDATE showtimes SET available_seats = available_seats - ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateShowtimeSQL)) {
                pstmt.setInt(1, selectedSeats.size());
                pstmt.setInt(2, selectedShowtime.getId());
                pstmt.executeUpdate();
            }
            
        } catch (SQLException e) {
            System.err.println("Error marking seats as booked: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void printReceipt() {
        try {
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPrintable(new TicketPrintable());
            
            // Show print dialog and print if user confirms
            if (printerJob.printDialog()) {
                printerJob.print();
            }
        } catch (PrinterException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Error printing receipt: " + e.getMessage(),
                "Print Error",
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Unexpected error during printing: " + e.getMessage(),
                "Print Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private class TicketPrintable implements Printable {
        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if (pageIndex > 0) {
                return NO_SUCH_PAGE;
            }
            
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            
            int pageWidth = (int) pageFormat.getImageableWidth();
            int y = 30;
            int lineHeight = 20;
            
            // Header
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.setColor(Color.BLACK);
            g2d.drawString("THE CINEMATE", pageWidth - 150, y);
            y += lineHeight;
            
            g2d.setFont(new Font("Arial", Font.BOLD, 22));
            g2d.drawString("CONFIRMATION FORM", 50, y);
            y += lineHeight * 2;
            
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("DETAIL CONFIRMATION", 50, y);
            y += lineHeight * 2;
            
            // Detail confirmation table header
            g2d.setFont(new Font("Arial", Font.BOLD, 10));
            g2d.setColor(Color.BLACK);
            
            // Draw table headers
            int tableX = 50;
            int colWidth = 75;
            g2d.drawString("MOVIE TITLE", tableX, y);
            g2d.drawString("COST", tableX + colWidth, y);
            g2d.drawString("QUANTITY", tableX + colWidth * 2, y);
            g2d.drawString("SEATS", tableX + colWidth * 3, y);
            g2d.drawString("TIME", tableX + colWidth * 4, y);
            g2d.drawString("SUBTOTAL", tableX + colWidth * 5, y);
            y += lineHeight;
            
            // Draw a line under headers
            g2d.drawLine(tableX, y - 5, tableX + colWidth * 6, y - 5);
            y += 5;
            
            // Movie details row
            g2d.setFont(new Font("Arial", Font.PLAIN, 9));
            String movieTitle = selectedMovie.getTitle();
            if (movieTitle.length() > 12) movieTitle = movieTitle.substring(0, 12) + "...";
            g2d.drawString(movieTitle, tableX, y);
            g2d.drawString("₱" + selectedMovie.getPrice().toString(), tableX + colWidth, y);
            g2d.drawString("1", tableX + colWidth * 2, y);
            
            // Seats
            StringBuilder seatList = new StringBuilder();
            for (int i = 0; i < selectedSeats.size(); i++) {
                seatList.append(selectedSeats.get(i).getSeatLabel());
                if (i < selectedSeats.size() - 1) seatList.append(",");
            }
            String seats = seatList.toString();
            if (seats.length() > 8) seats = seats.substring(0, 8) + "...";
            g2d.drawString(seats, tableX + colWidth * 3, y);
            
            // Time
            g2d.drawString(selectedShowtime.getFormattedShowDate() + " " + 
                          selectedShowtime.getFormattedShowTime(), tableX + colWidth * 4, y);
            g2d.drawString("₱" + totalPrice.toString(), tableX + colWidth * 5, y);
            y += lineHeight * 2;
            
            // Total
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("TOTAL: ₱" + totalPrice.toString(), pageWidth - 150, y);
            y += lineHeight * 3;
            
            // Personal details section
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("PERSONAL DETAILS:", 50, y);
            y += lineHeight * 2;
            
            // Customer details
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            g2d.drawString("NAME:", 50, y);
            g2d.setFont(new Font("Arial", Font.PLAIN, 11));
            g2d.drawString(customerName, 120, y);
            y += lineHeight * 2;
            
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            g2d.drawString("EMAIL:", 50, y);
            g2d.setFont(new Font("Arial", Font.PLAIN, 11));
            g2d.drawString(customerEmail, 120, y);
            y += lineHeight * 2;
            
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            g2d.drawString("PHONE:", 50, y);
            g2d.setFont(new Font("Arial", Font.PLAIN, 11));
            String phoneDisplay = (customerPhone == null || customerPhone.trim().isEmpty()) ? "Not provided" : customerPhone;
            g2d.drawString(phoneDisplay, 120, y);
            y += lineHeight * 3;
            
            // Booking information
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            g2d.drawString("BOOKING ID: " + bookingCode, 50, y);
            y += lineHeight;
            
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("MMMM d, yyyy");
            g2d.drawString("BOOKING DATE: " + LocalDate.now().format(formatter), 50, y);
            y += lineHeight * 2;
            
            // Footer
            g2d.setFont(new Font("Arial", Font.ITALIC, 10));
            g2d.drawString("Thank you for choosing THE CINEMATE!", 50, y);
            g2d.drawString("Please keep this receipt for your records.", 50, y + lineHeight);
            
            return PAGE_EXISTS;
        }
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
            
            new ConfirmationForm(testMovie, testShowtime, testSeats, testTickets, 
                               "Test Customer", "test@email.com", "", BigDecimal.valueOf(500)).setVisible(true);
        });
    }
} 