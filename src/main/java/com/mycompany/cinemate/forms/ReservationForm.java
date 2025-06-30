package com.mycompany.cinemate.forms;

import com.mycompany.cinemate.dao.MovieDAO;
import com.mycompany.cinemate.dao.ShowtimeDAO;
import com.mycompany.cinemate.models.Movie;
import com.mycompany.cinemate.models.Showtime;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReservationForm extends JFrame {
    private MovieDAO movieDAO;
    private ShowtimeDAO showtimeDAO;
    
    private JComboBox<Movie> movieComboBox;
    private JComboBox<String> dateComboBox;
    private JComboBox<Showtime> showtimeComboBox;
    private JLabel moviePosterLabel;
    private JTextArea movieInfoArea;
    private JButton proceedButton;
    private JButton backButton;
    
    private Movie selectedMovie;
    private Showtime selectedShowtime;
    
    public ReservationForm() {
        this(null); // Call parameterized constructor with null
    }
    
    public ReservationForm(Movie preSelectedMovie) {
        initializeDAOs();
        initializeComponents();
        initializeRenderers();
        setupLayout();
        setupEventListeners();
        loadMovies();
        
        // Pre-select movie if provided
        if (preSelectedMovie != null) {
            selectMovie(preSelectedMovie);
        }
        
        setTitle("CineMate - Book a Movie");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Apply cinema theme
        getContentPane().setBackground(Color.BLACK);
    }
    
    private void initializeDAOs() {
        movieDAO = new MovieDAO();
        showtimeDAO = new ShowtimeDAO();
    }
    
    private void initializeComponents() {
        movieComboBox = new JComboBox<>();
        movieComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        movieComboBox.setBackground(Color.WHITE);
        
        dateComboBox = new JComboBox<>();
        dateComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        dateComboBox.setBackground(Color.WHITE);
        
        showtimeComboBox = new JComboBox<>();
        showtimeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        showtimeComboBox.setBackground(Color.WHITE);
        
        moviePosterLabel = new JLabel();
        moviePosterLabel.setPreferredSize(new Dimension(200, 300));
        moviePosterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        moviePosterLabel.setBorder(BorderFactory.createLineBorder(new Color(255, 220, 0), 2));
        
        movieInfoArea = new JTextArea();
        movieInfoArea.setFont(new Font("Arial", Font.PLAIN, 12));
        movieInfoArea.setEditable(false);
        movieInfoArea.setLineWrap(true);
        movieInfoArea.setWrapStyleWord(true);
        movieInfoArea.setBackground(new Color(40, 40, 40));
        movieInfoArea.setForeground(Color.WHITE);
        movieInfoArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        proceedButton = new JButton("PROCEED TO SEAT SELECTION");
        proceedButton.setFont(new Font("Arial", Font.BOLD, 14));
        proceedButton.setBackground(new Color(255, 220, 0));
        proceedButton.setForeground(Color.BLACK);
        proceedButton.setPreferredSize(new Dimension(250, 40));
        proceedButton.setEnabled(false);
        
        backButton = new JButton("BACK TO MOVIES");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(153, 0, 0));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(150, 40));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(Color.BLACK);
        JLabel titleLabel = new JLabel("MOVIE RESERVATION");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(255, 220, 0));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Left panel - Movie poster
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.BLACK);
        leftPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255, 220, 0), 2),
            "Movie Poster", TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14), new Color(255, 220, 0)));
        leftPanel.add(moviePosterLabel, BorderLayout.CENTER);
        
        // Center panel - Selection form
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255, 220, 0), 2),
            "Booking Details", TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14), new Color(255, 220, 0)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Movie selection
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel movieLabel = new JLabel("Select Movie:");
        movieLabel.setFont(new Font("Arial", Font.BOLD, 14));
        movieLabel.setForeground(Color.WHITE);
        centerPanel.add(movieLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        centerPanel.add(movieComboBox, gbc);
        
        // Date selection
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel dateLabel = new JLabel("Select Date:");
        dateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        dateLabel.setForeground(Color.WHITE);
        centerPanel.add(dateLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        centerPanel.add(dateComboBox, gbc);
        
        // Showtime selection
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel showtimeLabel = new JLabel("Select Showtime:");
        showtimeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        showtimeLabel.setForeground(Color.WHITE);
        centerPanel.add(showtimeLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        centerPanel.add(showtimeComboBox, gbc);
        
        // Right panel - Movie info
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.BLACK);
        rightPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255, 220, 0), 2),
            "Movie Information", TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14), new Color(255, 220, 0)));
        
        JScrollPane scrollPane = new JScrollPane(movieInfoArea);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        scrollPane.setBackground(new Color(40, 40, 40));
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add panels to main
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Bottom panel - buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.add(backButton);
        bottomPanel.add(Box.createHorizontalStrut(20));
        bottomPanel.add(proceedButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventListeners() {
        movieComboBox.addActionListener(e -> {
            Movie movie = (Movie) movieComboBox.getSelectedItem();
            if (movie != null) {
                selectedMovie = movie;
                loadMoviePoster(movie);
                displayMovieInfo(movie);
                loadDatesForMovie(movie);
            }
        });
        
        dateComboBox.addActionListener(e -> {
            if (selectedMovie != null && dateComboBox.getSelectedItem() != null) {
                String selectedDate = (String) dateComboBox.getSelectedItem();
                LocalDate date = LocalDate.parse(selectedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                loadShowtimesForMovieAndDate(selectedMovie, date);
            }
        });
        
        showtimeComboBox.addActionListener(e -> {
            Showtime showtime = (Showtime) showtimeComboBox.getSelectedItem();
            if (showtime != null) {
                selectedShowtime = showtime;
                proceedButton.setEnabled(true);
            } else {
                proceedButton.setEnabled(false);
            }
        });
        
        proceedButton.addActionListener(e -> proceedToSeatSelection());
        
        backButton.addActionListener(e -> {
            dispose(); // Just close this form
        });
    }
    
    private void loadMovies() {
        movieComboBox.removeAllItems();
        List<Movie> movies = movieDAO.getAllActiveMovies();
        
        for (Movie movie : movies) {
            movieComboBox.addItem(movie);
        }
        
        if (!movies.isEmpty()) {
            movieComboBox.setSelectedIndex(0);
        }
    }
    
    private void selectMovie(Movie movieToSelect) {
        for (int i = 0; i < movieComboBox.getItemCount(); i++) {
            Movie movie = movieComboBox.getItemAt(i);
            if (movie != null && movie.getId() == movieToSelect.getId()) {
                movieComboBox.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void loadMoviePoster(Movie movie) {
        try {
            // Clean the poster path - remove leading slash if present
            String posterPath = movie.getPosterPath();
            if (posterPath.startsWith("/")) {
                posterPath = posterPath.substring(1);
            }
            
            java.net.URL posterUrl = getClass().getResource("/" + posterPath);
            if (posterUrl != null) {
                ImageIcon icon = new ImageIcon(posterUrl);
                Image img = icon.getImage().getScaledInstance(180, 270, Image.SCALE_SMOOTH);
                moviePosterLabel.setIcon(new ImageIcon(img));
                moviePosterLabel.setText(""); // Clear any text
            } else {
                moviePosterLabel.setIcon(null);
                moviePosterLabel.setText("No Poster Available");
                moviePosterLabel.setForeground(Color.WHITE);
                moviePosterLabel.setHorizontalAlignment(SwingConstants.CENTER);
            }
        } catch (Exception e) {
            moviePosterLabel.setIcon(null);
            moviePosterLabel.setText("No Poster Available");
            moviePosterLabel.setForeground(Color.WHITE);
            moviePosterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
    }
    
    private void displayMovieInfo(Movie movie) {
        StringBuilder info = new StringBuilder();
        info.append("TITLE: ").append(movie.getTitle()).append("\n\n");
        info.append("DESCRIPTION:\n").append(movie.getDescription()).append("\n\n");
        info.append("SHOWING DATES:\n").append(movie.getFormattedStartDate())
            .append(" to ").append(movie.getFormattedEndDate()).append("\n\n");
        info.append("PRICE: ").append(movie.getFormattedPrice());
        
        movieInfoArea.setText(info.toString());
        movieInfoArea.setCaretPosition(0);
    }
    
    private void loadDatesForMovie(Movie movie) {
        dateComboBox.removeAllItems();
        
        List<Showtime> showtimes = showtimeDAO.getShowtimesByMovieId(movie.getId());
        
        // Get unique dates
        showtimes.stream()
            .map(Showtime::getShowDate)
            .distinct()
            .sorted()
            .forEach(date -> dateComboBox.addItem(date.toString()));
        
        if (dateComboBox.getItemCount() > 0) {
            dateComboBox.setSelectedIndex(0);
        }
    }
    
    private void loadShowtimesForMovieAndDate(Movie movie, LocalDate date) {
        showtimeComboBox.removeAllItems();
        
        List<Showtime> showtimes = showtimeDAO.getShowtimesByMovieId(movie.getId());
        
        showtimes.stream()
            .filter(s -> s.getShowDate().equals(date))
            .filter(s -> s.getAvailableSeats() > 0)
            .sorted((s1, s2) -> s1.getShowTime().compareTo(s2.getShowTime()))
            .forEach(showtime -> showtimeComboBox.addItem(showtime));
        
        proceedButton.setEnabled(showtimeComboBox.getItemCount() > 0);
    }
    
    private void proceedToSeatSelection() {
        if (selectedMovie != null && selectedShowtime != null) {
            dispose();
            SeatsForm seatsForm = new SeatsForm(selectedMovie, selectedShowtime);
            seatsForm.setVisible(true);
        }
    }
    
    // Custom renderer for showtime combo box
    static class ShowtimeRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof Showtime) {
                Showtime showtime = (Showtime) value;
                setText(showtime.getFormattedShowTime() + " (" + showtime.getAvailableSeats() + " seats available)");
            }
            
            return this;
        }
    }
    
    // Custom renderer for movie combo box
    static class MovieRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof Movie) {
                Movie movie = (Movie) value;
                setText(movie.getTitle());
            }
            
            return this;
        }
    }
    
    // Initialize custom renderers
    private void initializeRenderers() {
        movieComboBox.setRenderer(new MovieRenderer());
        showtimeComboBox.setRenderer(new ShowtimeRenderer());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ReservationForm().setVisible(true);
        });
    }
} 