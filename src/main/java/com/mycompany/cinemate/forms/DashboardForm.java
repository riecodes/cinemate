package com.mycompany.cinemate.forms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.net.URL;

public class DashboardForm extends JFrame {
    private JLabel logoLabel;
    private JPanel statsPanel;
    private JTable scheduleTable;
    private DefaultTableModel tableModel;
    
    public DashboardForm() {
        initializeComponents();
        setupLayout();
        populateWithSampleData();
        setFrameProperties();
    }
    
    private void initializeComponents() {
        // Load logo image
        logoLabel = new JLabel();
        try {
            URL logoUrl = getClass().getResource("/assets/logo.png");
            if (logoUrl != null) {
                ImageIcon logoIcon = new ImageIcon(logoUrl);
                // Scale the logo to appropriate size
                Image img = logoIcon.getImage();
                Image scaledImg = img.getScaledInstance(200, 100, Image.SCALE_SMOOTH);
                logoLabel.setIcon(new ImageIcon(scaledImg));
            } else {
                logoLabel.setText("THE CINEMATE");
                logoLabel.setFont(new Font("Arial", Font.BOLD, 16));
                logoLabel.setForeground(Color.BLACK);
            }
        } catch (Exception e) {
            logoLabel.setText("THE CINEMATE");
            logoLabel.setFont(new Font("Arial", Font.BOLD, 16));
            logoLabel.setForeground(Color.BLACK);
        }
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Create statistics panel
        createStatsPanel();
        
        // Create schedule table
        createScheduleTable();
    }
    
    private void createStatsPanel() {
        statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 30, 50));
        statsPanel.setPreferredSize(new Dimension(900, 120)); // Set minimum height
        
        // Total Sales Card
        JPanel salesCard = createStatCard("TOTAL SALES:", "₱0.00");
        
        // Total Tickets Sold Card
        JPanel ticketsCard = createStatCard("TOTAL NO. OF TICKETS SOLD:", "0");
        
        // Number of Showing Card
        JPanel showingCard = createStatCard("NO. OF SHOWING", "0");
        
        statsPanel.add(salesCard);
        statsPanel.add(ticketsCard);
        statsPanel.add(showingCard);
    }
    
    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(139, 0, 0)); // Dark red
        card.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        card.setPreferredSize(new Dimension(280, 100));
        card.setMinimumSize(new Dimension(280, 100));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 20));
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(Box.createVerticalGlue());
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(valueLabel);
        card.add(Box.createVerticalGlue());
        
        return card;
    }
    
    private void createScheduleTable() {
        // Create table model
        String[] columnNames = {"MOVIE TITLE", "START", "END"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        // Create table
        scheduleTable = new JTable(tableModel);
        scheduleTable.setFont(new Font("Arial", Font.PLAIN, 14));
        scheduleTable.setRowHeight(30);
        scheduleTable.setGridColor(Color.BLACK);
        scheduleTable.setShowGrid(true);
        
        // Style the table header
        JTableHeader header = scheduleTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        // Set column widths
        scheduleTable.getColumnModel().getColumn(0).setPreferredWidth(400); // Movie Title
        scheduleTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Start
        scheduleTable.getColumnModel().getColumn(2).setPreferredWidth(150); // End
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        
        // Top panel with title and logo
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 50));
        
        JLabel titleLabel = new JLabel("//DASHBOARD FORM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(logoLabel, BorderLayout.EAST);
        
        // Center panel for table
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 50));
        
        // Showing date label
        JLabel showingDateLabel = new JLabel("SHOWING DATE");
        showingDateLabel.setFont(new Font("Arial", Font.BOLD, 24));
        showingDateLabel.setForeground(Color.BLACK);
        showingDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        showingDateLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Table scroll pane
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        scrollPane.setPreferredSize(new Dimension(700, 300));
        
        centerPanel.add(showingDateLabel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.SOUTH);
    }
    
    private void populateWithSampleData() {
        // Add sample data to demonstrate the table
        Object[][] sampleData = {
            {"", "", ""},
            {"", "", ""},
            {"", "", ""},
            {"", "", ""},
            {"", "", ""}
        };
        
        for (Object[] row : sampleData) {
            tableModel.addRow(row);
        }
    }
    
    private void setFrameProperties() {
        setTitle("The CineMate - Dashboard");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(true);
    }
    
    // Method to update statistics (to be called from other parts of the application)
    public void updateStats(double totalSales, int totalTickets, int numberOfShowing) {
        // This method can be used to update the dashboard with real data
        // For now, it's a placeholder for future implementation
    }
    
    // Method to refresh the movie schedule table
    public void refreshScheduleTable() {
        // Clear existing data
        tableModel.setRowCount(0);
        
        // Add updated data (placeholder for now)
        populateWithSampleData();
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
                new DashboardForm().setVisible(true);
            }
        });
    }
} 