package com.mycompany.cinemate.forms;

import com.mycompany.cinemate.dao.BookingDAO;
import com.mycompany.cinemate.dao.MovieDAO;
import com.mycompany.cinemate.dao.SeatDAO;
import com.mycompany.cinemate.dao.ShowtimeDAO;
import com.mycompany.cinemate.dao.StatisticsDAO;
import com.mycompany.cinemate.models.Booking;
import com.mycompany.cinemate.models.Movie;
import com.mycompany.cinemate.models.Seat;
import com.mycompany.cinemate.models.Showtime;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.math.BigDecimal;

public class AdminPanelForm extends JFrame {
    private JLabel logoLabel;
    private JTabbedPane tabbedPane;
    private JTable moviesTable;
    private JTable bookingsTable;
    private JTable usersTable;
    private DefaultTableModel moviesTableModel;
    private DefaultTableModel bookingsTableModel;
    private DefaultTableModel usersTableModel;
    private JButton addMovieBtn;
    private JButton editMovieBtn;
    private JButton deleteMovieBtn;
    private JButton refreshBtn;
    private JButton logoutBtn;
    private MovieDAO movieDAO;
    private ShowtimeDAO showtimeDAO;
    private SeatDAO seatDAO;
    private StatisticsDAO statisticsDAO;
    private BookingDAO bookingDAO;
    
    public AdminPanelForm() {
        movieDAO = new MovieDAO();
        showtimeDAO = new ShowtimeDAO();
        seatDAO = new SeatDAO();
        statisticsDAO = new StatisticsDAO();
        bookingDAO = new BookingDAO();
        initializeComponents();
        setupLayout();
        loadRealData();
        setFrameProperties();
    }
    
    private void initializeComponents() {
        createLogoLabel();
        createTabbedPane();
        createButtons();
    }
    
    private void createLogoLabel() {
        logoLabel = new JLabel();
        try {
            URL logoUrl = getClass().getResource("/assets/logo.png");
            if (logoUrl != null) {
                ImageIcon logoIcon = new ImageIcon(logoUrl);
                Image img = logoIcon.getImage();
                Image scaledImg = img.getScaledInstance(150, 75, Image.SCALE_SMOOTH);
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
    }
    
    private void createTabbedPane() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setForeground(Color.BLACK);
        
        // Dashboard Tab
        JPanel dashboardPanel = createDashboardPanel();
        tabbedPane.addTab("Dashboard", dashboardPanel);
        
        // Movies Management Tab
        JPanel moviesPanel = createMoviesPanel();
        tabbedPane.addTab("Movies Management", moviesPanel);
        
        // Bookings/Reservations Tab
        JPanel bookingsPanel = createBookingsPanel();
        tabbedPane.addTab("View Bookings", bookingsPanel);
        
        // Users Management Tab
        JPanel usersPanel = createUsersPanel();
        tabbedPane.addTab("User Management", usersPanel);
    }
    
    private JPanel createMoviesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Movies table
        String[] movieColumns = {"ID", "Title", "Description", "Start Date", "End Date", "Price", "Status"};
        moviesTableModel = new DefaultTableModel(movieColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        moviesTable = new JTable(moviesTableModel);
        moviesTable.setFont(new Font("Arial", Font.PLAIN, 12));
        moviesTable.setRowHeight(25);
        moviesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JTableHeader header = moviesTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setBackground(new Color(139, 0, 0));
        header.setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(moviesTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2));
        
        // Button panel for CRUD operations
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        addMovieBtn = createStyledButton("Add Movie", new Color(0, 150, 0));
        editMovieBtn = createStyledButton("Edit Movie", new Color(255, 140, 0));
        deleteMovieBtn = createStyledButton("Delete Movie", new Color(220, 20, 60));
        
        buttonPanel.add(addMovieBtn);
        buttonPanel.add(editMovieBtn);
        buttonPanel.add(deleteMovieBtn);
        
        // Add action listeners
        addMovieBtn.addActionListener(e -> showAddMovieDialog());
        editMovieBtn.addActionListener(e -> showEditMovieDialog());
        deleteMovieBtn.addActionListener(e -> deleteSelectedMovie());
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Bookings table
        String[] bookingColumns = {"Booking ID", "Customer Name", "Movie", "Date", "Seats", "Total Price", "Status"};
        bookingsTableModel = new DefaultTableModel(bookingColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        bookingsTable = new JTable(bookingsTableModel);
        bookingsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        bookingsTable.setRowHeight(25);
        
        JTableHeader header = bookingsTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setBackground(new Color(139, 0, 0));
        header.setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Users table
        String[] userColumns = {"User ID", "Username", "Email", "Registration Date", "Total Bookings"};
        usersTableModel = new DefaultTableModel(userColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        usersTable = new JTable(usersTableModel);
        usersTable.setFont(new Font("Arial", Font.PLAIN, 12));
        usersTable.setRowHeight(25);
        
        JTableHeader header = usersTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setBackground(new Color(139, 0, 0));
        header.setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(usersTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Title
        JLabel titleLabel = new JLabel("DASHBOARD", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(139, 0, 0));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        titleLabel.setPreferredSize(new Dimension(0, 60));
        
        // Main content panel with better space distribution
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(Color.WHITE);
        
        // Statistics cards panel with better sizing
        JPanel statsContainer = new JPanel(new BorderLayout());
        statsContainer.setBackground(Color.WHITE);
        statsContainer.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 25, 0));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setPreferredSize(new Dimension(0, 150)); // Ensure enough height
        
        // Create statistics cards with larger sizes
        JPanel totalSalesCard = createStatCard("Total Sales", "₱0.00", new Color(139, 0, 0));
        JPanel totalTicketsCard = createStatCard("Total No. of Tickets Sold", "0", new Color(139, 0, 0));
        JPanel showingCard = createStatCard("No. of Showing", "0", new Color(139, 0, 0));
        
        statsPanel.add(totalSalesCard);
        statsPanel.add(totalTicketsCard);
        statsPanel.add(showingCard);
        
        statsContainer.add(statsPanel, BorderLayout.CENTER);
        
        // Movie schedule table with improved sizing
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JLabel tableTitle = new JLabel("MOVIE SCHEDULE", SwingConstants.LEFT);
        tableTitle.setFont(new Font("Arial", Font.BOLD, 20));
        tableTitle.setForeground(Color.BLACK);
        tableTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        String[] scheduleColumns = {"Movie Title", "Start", "End"};
        DefaultTableModel scheduleModel = new DefaultTableModel(scheduleColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable scheduleTable = new JTable(scheduleModel);
        scheduleTable.setFont(new Font("Arial", Font.PLAIN, 13));
        scheduleTable.setRowHeight(28);
        scheduleTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        scheduleTable.getTableHeader().setBackground(new Color(139, 0, 0));
        scheduleTable.getTableHeader().setForeground(Color.WHITE);
        scheduleTable.getTableHeader().setPreferredSize(new Dimension(0, 35));
        
        // Load movie schedule data
        loadMovieSchedule(scheduleModel);
        
        JScrollPane scheduleScroll = new JScrollPane(scheduleTable);
        scheduleScroll.setPreferredSize(new Dimension(0, 350)); // Increased height
        scheduleScroll.setMinimumSize(new Dimension(0, 250));
        scheduleScroll.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2));
        scheduleScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scheduleScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(scheduleScroll, BorderLayout.CENTER);
        
        // Assemble main content
        mainContentPanel.add(statsContainer, BorderLayout.NORTH);
        mainContentPanel.add(tablePanel, BorderLayout.CENTER);
        
        // Update statistics
        updateDashboardStats(totalSalesCard, totalTicketsCard, showingCard);
        
        // Add refresh button for dashboard
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(139, 0, 0));
        JButton refreshDataBtn = createStyledButton("Refresh Data", new Color(34, 139, 34));
        refreshDataBtn.addActionListener(e -> {
            refreshAllData();
        });
        bottomPanel.add(refreshDataBtn);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(mainContentPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        card.setPreferredSize(new Dimension(300, 140));
        card.setMinimumSize(new Dimension(250, 120));
        
        // Title with better text wrapping
        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'>" + title + "</div></html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 8, 5, 8));
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 26));
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 15, 10));
        valueLabel.setVerticalAlignment(SwingConstants.CENTER);
        
        // Create a center panel for the value to ensure it's properly centered
        JPanel valuePanel = new JPanel(new BorderLayout());
        valuePanel.setBackground(color);
        valuePanel.add(valueLabel, BorderLayout.CENTER);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valuePanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private void loadMovieSchedule(DefaultTableModel model) {
        try {
            List<Movie> allMovies = movieDAO.getAllMovies();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
            
            for (Movie movie : allMovies) {
                if (movie.isActive()) { // Only show active movies
                    Object[] row = {
                        movie.getTitle(),
                        dateFormat.format(java.sql.Date.valueOf(movie.getStartDate())),
                        dateFormat.format(java.sql.Date.valueOf(movie.getEndDate()))
                    };
                    model.addRow(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void updateDashboardStats(JPanel salesCard, JPanel ticketsCard, JPanel showingCard) {
        try {
            // Get statistics from DAO
            BigDecimal totalSales = statisticsDAO.getTotalSales();
            int totalTickets = statisticsDAO.getTotalTicketsSold();
            int numberOfShowing = statisticsDAO.getNumberOfShowings();
            
            // Debug output
            System.out.println("Dashboard Stats - Total Sales: " + totalSales + ", Total Tickets: " + totalTickets + ", Showings: " + numberOfShowing);
            
            // Update card values
            updateStatCardValue(salesCard, "₱" + totalSales.toString());
            updateStatCardValue(ticketsCard, String.valueOf(totalTickets));
            updateStatCardValue(showingCard, String.valueOf(numberOfShowing));
            
        } catch (Exception e) {
            System.err.println("Error updating dashboard statistics: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void updateStatCardValue(JPanel card, String newValue) {
        Component[] components = card.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel valuePanel = (JPanel) component;
                Component[] innerComponents = valuePanel.getComponents();
                for (Component innerComponent : innerComponents) {
                    if (innerComponent instanceof JLabel) {
                        JLabel label = (JLabel) innerComponent;
                        if (label.getFont().getSize() == 26) { // This is the value label
                            label.setText(newValue);
                            card.repaint();
                            return;
                        }
                    }
                }
            }
        }
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setPreferredSize(new Dimension(140, 35));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        
        // Hover effect
        Color hoverColor = bgColor.brighter();
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
                button.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
                button.repaint();
            }
        });
        
        return button;
    }
    
    private void createButtons() {
        refreshBtn = new JButton("Refresh Data");
        refreshBtn.setFont(new Font("Arial", Font.BOLD, 14));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setBackground(new Color(30, 144, 255));
        refreshBtn.setPreferredSize(new Dimension(150, 40));
        refreshBtn.setFocusPainted(false);
        refreshBtn.setBorderPainted(true);
        refreshBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        refreshBtn.setOpaque(true);
        refreshBtn.setContentAreaFilled(true);
        
        logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 14));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setBackground(new Color(139, 0, 0));
        logoutBtn.setPreferredSize(new Dimension(120, 40));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorderPainted(true);
        logoutBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        logoutBtn.setOpaque(true);
        logoutBtn.setContentAreaFilled(true);
        
        // Add hover effects
        addHoverEffect(refreshBtn, new Color(30, 144, 255), new Color(70, 180, 255));
        addHoverEffect(logoutBtn, new Color(139, 0, 0), new Color(180, 0, 0));
        
        // Action listeners
        refreshBtn.addActionListener(e -> refreshAllData());
        logoutBtn.addActionListener(e -> logout());
    }
    
    private void addHoverEffect(JButton button, Color normalColor, Color hoverColor) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
                button.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normalColor);
                button.repaint();
            }
        });
    }
    
    private void loadRealData() {
        try {
            loadMoviesData();
            loadBookingsData();
            loadUsersData();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error loading data from database: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadMoviesData() {
        try {
            moviesTableModel.setRowCount(0);
            List<Movie> movies = movieDAO.getAllMovies(); // Get all movies including inactive
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
            
            for (Movie movie : movies) {
                Object[] row = {
                    movie.getId(),
                    movie.getTitle(),
                    movie.getDescription().length() > 50 ? 
                        movie.getDescription().substring(0, 50) + "..." : 
                        movie.getDescription(),
                    dateFormat.format(java.sql.Date.valueOf(movie.getStartDate())),
                    dateFormat.format(java.sql.Date.valueOf(movie.getEndDate())),
                    "₱" + movie.getPrice(),
                    movie.isActive() ? "Active" : "Inactive"
                };
                moviesTableModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error loading movies: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadBookingsData() {
        try {
            bookingsTableModel.setRowCount(0);
            List<Booking> bookings = bookingDAO.getAllBookings();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
            
            for (Booking booking : bookings) {
                // Get movie title from MovieDAO
                Movie movie = movieDAO.getMovieById(booking.getMovieId());
                String movieTitle = movie != null ? movie.getTitle() : "Unknown Movie";
                
                Object[] row = {
                    booking.getId(),
                    booking.getCustomerName(),
                    movieTitle,
                    dateFormat.format(java.sql.Date.valueOf(booking.getBookingDate())),
                    booking.getSeats(),
                    "₱" + booking.getTotalPrice().toString(),
                    booking.getBookingStatus().toString()
                };
                bookingsTableModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error loading bookings: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadUsersData() {
        try {
            usersTableModel.setRowCount(0);
            
            // Get unique users from bookings
            List<Booking> allBookings = bookingDAO.getAllBookings();
            java.util.Map<String, Object[]> uniqueUsers = new java.util.HashMap<>();
            
            for (Booking booking : allBookings) {
                String email = booking.getCustomerEmail();
                if (!uniqueUsers.containsKey(email)) {
                    // Count total bookings for this user
                    long userBookingCount = allBookings.stream()
                        .filter(b -> b.getCustomerEmail().equals(email))
                        .count();
                    
                    // Find earliest booking date for registration date
                    String regDate = allBookings.stream()
                        .filter(b -> b.getCustomerEmail().equals(email))
                        .map(b -> b.getCreatedAt())
                        .min(java.util.Comparator.comparing(java.time.LocalDateTime::toLocalDate))
                        .map(date -> new SimpleDateFormat("MMM dd, yyyy").format(
                            java.sql.Date.valueOf(date.toLocalDate())))
                        .orElse("Unknown");
                    
                    Object[] userData = {
                        uniqueUsers.size() + 1, // User ID (sequential)
                        booking.getCustomerName(),
                        email,
                        regDate,
                        userBookingCount
                    };
                    uniqueUsers.put(email, userData);
                }
            }
            
            // Add all unique users to table
            for (Object[] userData : uniqueUsers.values()) {
                usersTableModel.addRow(userData);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error loading users: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showAddMovieDialog() {
        JDialog dialog = new JDialog(this, "Add New Movie", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Form fields
        JTextField titleField = new JTextField(20);
        JTextArea descArea = new JTextArea(4, 20);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descArea);
        
                    JTextField startDateField = new JTextField("2025-06-30", 15);
                    JTextField endDateField = new JTextField("2025-07-20", 15);
        JTextField priceField = new JTextField("200.00", 15);
        JTextField posterField = new JTextField("/assets/", 20);
        
        // Layout components
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        formPanel.add(titleField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        formPanel.add(descScroll, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Start Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        formPanel.add(startDateField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("End Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        formPanel.add(endDateField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Price (₱):"), gbc);
        gbc.gridx = 1;
        formPanel.add(priceField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Poster Path (e.g., /assets/movie.png):"), gbc);
        gbc.gridx = 1;
        formPanel.add(posterField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveBtn = new JButton("Save Movie");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.addActionListener(e -> {
            try {
                String title = titleField.getText().trim();
                String description = descArea.getText().trim();
                String startDate = startDateField.getText().trim();
                String endDate = endDateField.getText().trim();
                String priceStr = priceField.getText().trim();
                String posterPath = posterField.getText().trim();
                
                if (title.isEmpty() || description.isEmpty() || startDate.isEmpty() || 
                    endDate.isEmpty() || priceStr.isEmpty() || posterPath.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please fill all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (!posterPath.startsWith("/assets/")) {
                    JOptionPane.showMessageDialog(dialog, "Poster path must start with '/assets/' (e.g., /assets/movie.png)", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                BigDecimal price = new BigDecimal(priceStr);
                
                Movie newMovie = new Movie();
                newMovie.setTitle(title);
                newMovie.setDescription(description);
                newMovie.setStartDate(java.time.LocalDate.parse(startDate));
                newMovie.setEndDate(java.time.LocalDate.parse(endDate));
                newMovie.setPrice(price);
                newMovie.setPosterPath(posterPath);
                newMovie.setActive(true);
                
                boolean movieAdded = movieDAO.addMovie(newMovie);
                
                if (movieAdded) {
                    // Auto-create showtimes for the new movie
                    createDefaultShowtimesForMovie(newMovie);
                
                JOptionPane.showMessageDialog(dialog, "Movie added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                loadMoviesData(); // Refresh the table
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to add movie.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Error adding movie: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
        private void showEditMovieDialog() {
        int selectedRow = moviesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a movie to edit.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get the selected movie data
        int movieId = (Integer) moviesTableModel.getValueAt(selectedRow, 0);
        String currentTitle = (String) moviesTableModel.getValueAt(selectedRow, 1);
        String currentStatus = (String) moviesTableModel.getValueAt(selectedRow, 6);
        
        try {
            // Load full movie details from database
            Movie currentMovie = movieDAO.getMovieById(movieId);
            if (currentMovie == null) {
                JOptionPane.showMessageDialog(this, 
                    "Movie not found in database.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create edit dialog
            JDialog dialog = new JDialog(this, "Edit Movie - " + currentTitle, true);
            dialog.setSize(600, 500);
            dialog.setLocationRelativeTo(this);
            dialog.setLayout(new BorderLayout());
            
            // Form panel
            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.WEST;
            
            // Title field
            gbc.gridx = 0; gbc.gridy = 0;
            formPanel.add(new JLabel("Title:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
            JTextField titleField = new JTextField(currentMovie.getTitle(), 20);
            formPanel.add(titleField, gbc);
            
            // Description area
            gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
            formPanel.add(new JLabel("Description:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
            JTextArea descArea = new JTextArea(currentMovie.getDescription(), 8, 30);
            descArea.setLineWrap(true);
            descArea.setWrapStyleWord(true);
            JScrollPane descScroll = new JScrollPane(descArea);
            formPanel.add(descScroll, gbc);
            
            // Reset constraints for remaining fields
            gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 0; gbc.weighty = 0;
            
            // Start date field
            gbc.gridx = 0; gbc.gridy = 2;
            formPanel.add(new JLabel("Start Date (YYYY-MM-DD):"), gbc);
            gbc.gridx = 1;
            JTextField startDateField = new JTextField(currentMovie.getStartDate().toString(), 15);
            formPanel.add(startDateField, gbc);
            
            // End date field
            gbc.gridx = 0; gbc.gridy = 3;
            formPanel.add(new JLabel("End Date (YYYY-MM-DD):"), gbc);
            gbc.gridx = 1;
            JTextField endDateField = new JTextField(currentMovie.getEndDate().toString(), 15);
            formPanel.add(endDateField, gbc);
            
            // Price field
            gbc.gridx = 0; gbc.gridy = 4;
            formPanel.add(new JLabel("Price (₱):"), gbc);
            gbc.gridx = 1;
            JTextField priceField = new JTextField(String.valueOf(currentMovie.getPrice()), 15);
            formPanel.add(priceField, gbc);
            
            // Poster path field
            gbc.gridx = 0; gbc.gridy = 5;
            formPanel.add(new JLabel("Poster Path (e.g., /assets/movie.png):"), gbc);
            gbc.gridx = 1;
            JTextField posterField = new JTextField(currentMovie.getPosterPath(), 30);
            formPanel.add(posterField, gbc);
            
            // Active status checkbox
            gbc.gridx = 0; gbc.gridy = 6;
            formPanel.add(new JLabel("Active:"), gbc);
            gbc.gridx = 1;
            JCheckBox activeCheckbox = new JCheckBox("Movie is active", currentMovie.isActive());
            formPanel.add(activeCheckbox, gbc);
            
            // Button panel
            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton updateBtn = createStyledButton("Update Movie", new Color(34, 139, 34));
            JButton cancelBtn = createStyledButton("Cancel", new Color(139, 0, 0));
            
            // Update button action
            updateBtn.addActionListener(e -> {
                try {
                    // Validate inputs
                    String title = titleField.getText().trim();
                    String description = descArea.getText().trim();
                    String startDateStr = startDateField.getText().trim();
                    String endDateStr = endDateField.getText().trim();
                    String priceStr = priceField.getText().trim();
                    String posterPath = posterField.getText().trim();
                    boolean isActive = activeCheckbox.isSelected();
                    
                    if (title.isEmpty() || description.isEmpty() || 
                        startDateStr.isEmpty() || endDateStr.isEmpty() || 
                        priceStr.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, 
                            "Please fill in all required fields.", 
                            "Validation Error", 
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    if (!posterPath.isEmpty() && !posterPath.startsWith("/assets/")) {
                        JOptionPane.showMessageDialog(dialog, 
                            "Poster path must start with '/assets/' (e.g., /assets/movie.png)", 
                            "Validation Error", 
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    // Parse and validate dates
                    java.time.LocalDate startDate = java.time.LocalDate.parse(startDateStr);
                    java.time.LocalDate endDate = java.time.LocalDate.parse(endDateStr);
                    
                    if (endDate.isBefore(startDate)) {
                        JOptionPane.showMessageDialog(dialog, 
                            "End date must be after start date.", 
                            "Validation Error", 
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    // Parse price
                    double price = Double.parseDouble(priceStr);
                    if (price <= 0) {
                        JOptionPane.showMessageDialog(dialog, 
                            "Price must be greater than 0.", 
                            "Validation Error", 
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    // Update movie in database
                    Movie updatedMovie = new Movie(
                        movieId, title, description, startDate, endDate, 
                        new java.math.BigDecimal(price), posterPath, isActive
                    );
                    
                    boolean success = movieDAO.updateMovie(updatedMovie);
                    
                    if (success) {
                        JOptionPane.showMessageDialog(dialog, 
                            "Movie updated successfully!", 
                            "Success", 
                            JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                        loadMoviesData(); // Refresh table
                    } else {
                        JOptionPane.showMessageDialog(dialog, 
                            "Failed to update movie. Please check your inputs.", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                    
                } catch (java.time.format.DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Invalid date format. Please use YYYY-MM-DD.", 
                        "Date Format Error", 
                        JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Invalid price format. Please enter a valid number.", 
                        "Price Format Error", 
                        JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, 
                        "Error updating movie: " + ex.getMessage(), 
                        "Database Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            });
            
            cancelBtn.addActionListener(e -> dialog.dispose());
            
            buttonPanel.add(updateBtn);
            buttonPanel.add(cancelBtn);
            
            dialog.add(formPanel, BorderLayout.CENTER);
            dialog.add(buttonPanel, BorderLayout.SOUTH);
            dialog.setVisible(true);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error loading movie details: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteSelectedMovie() {
        int selectedRow = moviesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a movie to delete.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int movieId = (Integer) moviesTableModel.getValueAt(selectedRow, 0);
        String movieTitle = (String) moviesTableModel.getValueAt(selectedRow, 1);
        String currentStatus = (String) moviesTableModel.getValueAt(selectedRow, 6);
        
        String message = currentStatus.equals("Active") ? 
            "This will mark the movie as INACTIVE (soft delete):\n" + movieTitle + "\n\nIt will be hidden from customers but remain in the database." :
            "This movie is already inactive. Do you want to reactivate it?\n" + movieTitle;
            
        int result = JOptionPane.showConfirmDialog(this,
            message,
            currentStatus.equals("Active") ? "Confirm Soft Delete" : "Confirm Reactivate",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (result == JOptionPane.YES_OPTION) {
            try {
                if (currentStatus.equals("Active")) {
                    movieDAO.deleteMovie(movieId); // Soft delete - marks as inactive
                    JOptionPane.showMessageDialog(this, 
                        "Movie marked as inactive successfully!\nCustomers will no longer see this movie.", 
                        "Soft Delete Complete", 
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    movieDAO.reactivateMovie(movieId); // Reactivate the movie
                    JOptionPane.showMessageDialog(this, 
                        "Movie reactivated successfully!\nCustomers can now see this movie again.", 
                        "Reactivation Complete", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
                loadMoviesData(); // Refresh the table
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                    "Error updating movie status: " + e.getMessage(), 
                    "Database Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void refreshAllData() {
        try {
        loadRealData();
            
            // Also refresh dashboard statistics if they exist
            refreshDashboardStatistics();
            
        JOptionPane.showMessageDialog(this, 
            "All data refreshed successfully!", 
            "Refresh Complete", 
            JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error refreshing data: " + e.getMessage(), 
                "Refresh Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Public method to refresh data from external sources
    public void refreshData() {
        refreshAllData();
    }
    
    private void refreshDashboardStatistics() {
        // Find the dashboard panel (first tab)
        if (tabbedPane.getComponentCount() > 0) {
            Component dashboardComponent = tabbedPane.getComponentAt(0);
            if (dashboardComponent instanceof JPanel) {
                JPanel dashboardPanel = (JPanel) dashboardComponent;
                
                // Update dashboard statistics cards
        findAndUpdateStatsCards(dashboardPanel);
                
                // Also refresh the movie schedule table if it exists
                refreshDashboardMovieSchedule(dashboardPanel);
            }
        }
    }
    
    private void refreshDashboardMovieSchedule(JPanel dashboardPanel) {
        // Find the movie schedule table in the dashboard
        java.util.List<JTable> tables = findComponentsOfType(dashboardPanel, JTable.class);
        for (JTable table : tables) {
            if (table.getModel() instanceof DefaultTableModel) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                // Check if this looks like the movie schedule table (3 columns)
                if (model.getColumnCount() == 3) {
                    model.setRowCount(0); // Clear existing data
                    loadMovieSchedule(model);
                    System.out.println("Movie schedule refreshed");
                    break;
                }
            }
        }
    }
    
    private void findAndUpdateStatsCards(Container container) {
        // Store references to the stats cards when we find them
        JPanel salesCard = null;
        JPanel ticketsCard = null; 
        JPanel showingCard = null;
        
        // Search for stats cards in the container hierarchy
        java.util.List<JPanel> potentialStatsCards = new java.util.ArrayList<>();
        collectStatsPanels(container, potentialStatsCards);
        
        // Try to identify the three stats cards by looking for specific patterns
        for (JPanel panel : potentialStatsCards) {
            String cardTitle = getStatCardTitle(panel);
            if (cardTitle != null) {
                if (cardTitle.toLowerCase().contains("sales")) {
                    salesCard = panel;
                } else if (cardTitle.toLowerCase().contains("tickets")) {
                    ticketsCard = panel;
                } else if (cardTitle.toLowerCase().contains("showing")) {
                    showingCard = panel;
                }
            }
        }
        
        // Update the stats if we found the cards
        if (salesCard != null && ticketsCard != null && showingCard != null) {
            updateDashboardStats(salesCard, ticketsCard, showingCard);
            System.out.println("Dashboard statistics updated successfully");
        } else {
            System.out.println("Could not find all dashboard stats cards - Sales: " + (salesCard != null) + 
                             ", Tickets: " + (ticketsCard != null) + ", Showing: " + (showingCard != null));
        }
    }
    
    private void collectStatsPanels(Container container, java.util.List<JPanel> panels) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                
                // Check if this looks like a stats card (has a border and contains labels)
                if (panel.getBorder() != null && hasStatsCardStructure(panel)) {
                    panels.add(panel);
                    }
                
                // Recursively search in child panels
                collectStatsPanels(panel, panels);
            }
        }
    }
    
    private boolean hasStatsCardStructure(JPanel panel) {
        // A stats card should have at least one label with a large font (for the value)
        return findComponentsOfType(panel, JLabel.class).stream()
               .anyMatch(label -> label.getFont().getSize() >= 24);
    }
    
    private String getStatCardTitle(JPanel card) {
        // Look for the title label in the stats card
        java.util.List<JLabel> labels = findComponentsOfType(card, JLabel.class);
        for (JLabel label : labels) {
            if (label.getFont().getSize() <= 16) { // Title labels are smaller
                return label.getText();
            }
        }
        return null;
    }
    
    private <T extends Component> java.util.List<T> findComponentsOfType(Container container, Class<T> type) {
        java.util.List<T> components = new java.util.ArrayList<>();
        findComponentsOfTypeRecursive(container, type, components);
        return components;
    }
    
    private <T extends Component> void findComponentsOfTypeRecursive(Container container, Class<T> type, java.util.List<T> components) {
        for (Component component : container.getComponents()) {
            if (type.isInstance(component)) {
                components.add(type.cast(component));
            }
            if (component instanceof Container) {
                findComponentsOfTypeRecursive((Container) component, type, components);
            }
        }
    }
    
    private void logout() {
        int result = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION);
            
        if (result == JOptionPane.YES_OPTION) {
            dispose();
        }
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(139, 0, 0));
        
        // Top panel with title and logo
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(139, 0, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 15, 30));
        
        JLabel titleLabel = new JLabel("ADMIN PANEL - THE CINEMATE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        // Logo section
        JPanel logoSection = new JPanel(new BorderLayout());
        logoSection.setBackground(new Color(180, 0, 0, 100));
        logoSection.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        logoSection.setPreferredSize(new Dimension(180, 100));
        logoSection.add(logoLabel, BorderLayout.CENTER);
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(logoSection, BorderLayout.EAST);
        
        // Center panel with tabbed pane
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        centerPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Bottom panel with control buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        bottomPanel.setBackground(new Color(139, 0, 0));
        bottomPanel.add(refreshBtn);
        bottomPanel.add(logoutBtn);
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setFrameProperties() {
        setTitle("The CineMate - Admin Panel");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Add window focus listener to auto-refresh data when window gains focus
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            @Override
            public void windowGainedFocus(java.awt.event.WindowEvent e) {
                // Refresh data silently when window gains focus
                loadRealData();
                // Update dashboard statistics if dashboard tab is visible
                if (tabbedPane.getSelectedIndex() == 0) { // Dashboard is first tab
                    SwingUtilities.invokeLater(() -> {
                        // Re-select dashboard tab to trigger a refresh
                        int currentTab = tabbedPane.getSelectedIndex();
                        if (currentTab == 0) {
                            tabbedPane.revalidate();
                            tabbedPane.repaint();
                        }
                    });
                }
            }
            
            @Override
            public void windowLostFocus(java.awt.event.WindowEvent e) {
                // Do nothing when losing focus
            }
        });
    }
    
    /**
     * Creates default showtimes for a newly added movie
     * This ensures that new movies can be booked immediately
     */
    private void createDefaultShowtimesForMovie(Movie movie) {
        try {
            java.time.LocalDate startDate = movie.getStartDate();
            java.time.LocalDate endDate = movie.getEndDate();
            
            // Create showtimes for the first 3 days of the movie's run
            java.time.LocalDate currentDate = startDate;
            java.time.LocalDate maxDate = startDate.plusDays(2); // Create showtimes for 3 days
            if (maxDate.isAfter(endDate)) {
                maxDate = endDate; // Don't exceed the movie's end date
            }
            
            // Standard showtimes: 10:00, 14:00, 18:00, 21:00
            java.time.LocalTime[] times = {
                java.time.LocalTime.of(10, 0),
                java.time.LocalTime.of(14, 0), 
                java.time.LocalTime.of(18, 0),
                java.time.LocalTime.of(21, 0)
            };
            
            while (!currentDate.isAfter(maxDate)) {
                final java.time.LocalDate dateForThisIteration = currentDate; // Make effectively final for lambda
                
                for (java.time.LocalTime time : times) {
                    Showtime showtime = new Showtime();
                    showtime.setMovieId(movie.getId());
                    showtime.setShowDate(dateForThisIteration);
                    showtime.setShowTime(time);
                    showtime.setAvailableSeats(48); // Standard theater capacity
                    
                    boolean showtimeAdded = showtimeDAO.addShowtime(showtime);
                    
                    if (showtimeAdded) {
                        // Get the showtime ID from database to create seats
                        java.util.List<Showtime> showtimes = showtimeDAO.getShowtimesByMovieId(movie.getId());
                        Showtime addedShowtime = showtimes.stream()
                            .filter(s -> s.getShowDate().equals(dateForThisIteration) && s.getShowTime().equals(time))
                            .findFirst()
                            .orElse(null);
                            
                        if (addedShowtime != null) {
                            createSeatsForShowtime(addedShowtime);
                        }
                    }
                }
                currentDate = currentDate.plusDays(1);
            }
            
            System.out.println("Created default showtimes for movie: " + movie.getTitle());
            
        } catch (Exception e) {
            System.err.println("Error creating default showtimes for movie " + movie.getTitle() + ": " + e.getMessage());
            e.printStackTrace();
            // Don't throw the exception - movie was still created successfully
        }
    }
    // 
    /**
     * Creates seats for a specific showtime
     * Theater layout: 6 rows (A-F) with 8 seats per row (1-8)
     */
    private void createSeatsForShowtime(Showtime showtime) {
        try {
            // Create seats: 6 rows (A-F), 8 seats per row (1-8) = 48 total seats
            for (char row = 'A'; row <= 'F'; row++) {
                for (int seatNumber = 1; seatNumber <= 8; seatNumber++) {
                    Seat seat = new Seat();
                    seat.setShowtimeId(showtime.getId());
                    seat.setSeatRow(row);
                    seat.setSeatNumber(seatNumber);
                    seat.setBooked(false);
                    
                    seatDAO.addSeat(seat);
                }
            }
            
            System.out.println("Created 48 seats for showtime: " + showtime.getFormattedShowDate() + " " + showtime.getFormattedShowTime());
            
        } catch (Exception e) {
            System.err.println("Error creating seats for showtime " + showtime.getId() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new AdminPanelForm().setVisible(true));
    }
} 