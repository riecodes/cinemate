package com.mycompany.cinemate.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class AdminLoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    private JLabel logoLabel;
    private JLabel statusLabel;
    
    // Fixed admin credentials
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    
    public AdminLoginForm() {
        initializeComponents();
        setupLayout();
        setFrameProperties();
    }
    
    private void initializeComponents() {
        createLogoLabel();
        createInputFields();
        createButtons();
        createStatusLabel();
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
                logoLabel.setFont(new Font("Arial", Font.BOLD, 18));
                logoLabel.setForeground(Color.WHITE);
            }
        } catch (Exception e) {
            logoLabel.setText("THE CINEMATE");
            logoLabel.setFont(new Font("Arial", Font.BOLD, 18));
            logoLabel.setForeground(Color.WHITE);
        }
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    private void createInputFields() {
        usernameField = new JTextField(20); // Increased field width
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16)); // Larger font
        usernameField.setPreferredSize(new Dimension(300, 40)); // Set explicit size
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15) // Increased padding
        ));
        
        passwordField = new JPasswordField(20); // Increased field width
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16)); // Larger font
        passwordField.setPreferredSize(new Dimension(300, 40)); // Set explicit size
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15) // Increased padding
        ));
        
        // Add action listeners for Enter key
        usernameField.addActionListener(e -> passwordField.requestFocus());
        passwordField.addActionListener(e -> performLogin());
    }
    
    private void createButtons() {
        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(139, 0, 0));
        loginButton.setPreferredSize(new Dimension(140, 50)); // Larger buttons
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(true);
        loginButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20) // Increased padding
        ));
        loginButton.setOpaque(true);
        loginButton.setContentAreaFilled(true);
        
        cancelButton = new JButton("CANCEL");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(140, 50)); // Larger buttons
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(true);
        cancelButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20) // Increased padding to match login button
        ));
        cancelButton.setOpaque(true);
        cancelButton.setContentAreaFilled(true);
        
        // Add hover effects
        addHoverEffect(loginButton, new Color(139, 0, 0), new Color(180, 0, 0));
        addHoverEffect(cancelButton, Color.WHITE, Color.LIGHT_GRAY);
        
        // Action listeners
        loginButton.addActionListener(e -> performLogin());
        cancelButton.addActionListener(e -> dispose());
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
    
    private void createStatusLabel() {
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
    }
    
    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter both username and password!");
            return;
        }
        
        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            // Successful login
            statusLabel.setForeground(new Color(0, 150, 0));
            statusLabel.setText("Login successful! Opening admin panel...");
            
            // Delay to show success message
            Timer timer = new Timer(1000, e -> {
                dispose();
                SwingUtilities.invokeLater(() -> new AdminPanelForm().setVisible(true));
            });
            timer.setRepeats(false);
            timer.start();
            
        } else {
            showError("Invalid credentials! Please try again.");
            passwordField.setText("");
            usernameField.requestFocus();
        }
    }
    
    private void showError(String message) {
        statusLabel.setForeground(Color.RED);
        statusLabel.setText(message);
        
        // Clear error message after 3 seconds
        Timer timer = new Timer(3000, e -> statusLabel.setText(" "));
        timer.setRepeats(false);
        timer.start();
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(139, 0, 0));
        
        // Main login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 3),
            BorderFactory.createEmptyBorder(40, 50, 40, 50)  // Increased padding
        ));
        
        // Title
        JLabel titleLabel = new JLabel("ADMIN LOGIN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(139, 0, 0));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Administrator Access Required");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        subtitleLabel.setForeground(Color.GRAY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Form fields
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10)); // Increased spacing
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        
        // Add components with spacing
        loginPanel.add(logoLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(titleLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        loginPanel.add(subtitleLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        loginPanel.add(usernameLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        loginPanel.add(usernameField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        loginPanel.add(passwordLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        loginPanel.add(passwordField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(statusLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(buttonPanel);
        
        // Center the login panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(139, 0, 0));
        centerPanel.add(loginPanel);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Info panel at bottom
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel.setBackground(new Color(139, 0, 0));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 30, 30)); // Increased padding
        
        JLabel infoLabel = new JLabel("Default credentials: admin / admin");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        infoLabel.setForeground(new Color(255, 215, 0));
        
        infoPanel.add(infoLabel);
        add(infoPanel, BorderLayout.SOUTH);
    }
    
    private void setFrameProperties() {
        setTitle("The CineMate - Admin Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 650); // Increased size to prevent cut-off
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(500, 600));
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new AdminLoginForm().setVisible(true));
    }
} 