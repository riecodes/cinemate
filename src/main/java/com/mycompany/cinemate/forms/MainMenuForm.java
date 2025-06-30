package com.mycompany.cinemate.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class MainMenuForm extends JFrame {
    private JButton moviesBtn;
    private JButton ticketsBtn;
    private JLabel logoLabel;
    private JLabel welcomeLabel;
    
    public MainMenuForm() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
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
                Image scaledImg = img.getScaledInstance(300, 150, Image.SCALE_SMOOTH);
                logoLabel.setIcon(new ImageIcon(scaledImg));
            } else {
                logoLabel.setText("THE CINEMATE");
                logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
                logoLabel.setForeground(Color.WHITE);
            }
        } catch (Exception e) {
            logoLabel.setText("THE CINEMATE");
            logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
            logoLabel.setForeground(Color.WHITE);
        }
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Welcome label
        welcomeLabel = new JLabel("WELCOME");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 48));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Create navigation buttons
        moviesBtn = createStyledButton("MOVIES");
        ticketsBtn = createStyledButton("TICKETS");
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setPreferredSize(new Dimension(250, 80));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        
        // Ensure the button is opaque and content area is filled
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        
        // Force the button to show custom colors
        button.putClientProperty("JButton.buttonType", "normal");
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.DARK_GRAY);
                button.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.BLACK);
                button.repaint();
            }
        });
        
        return button;
    }
    
    private JButton createStyledButtonWithIcon(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setPreferredSize(new Dimension(250, 80));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        
        // Load and set icon
        try {
            URL iconUrl = getClass().getResource(iconPath);
            if (iconUrl != null) {
                ImageIcon icon = new ImageIcon(iconUrl);
                Image img = icon.getImage();
                Image scaledImg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaledImg));
                button.setHorizontalTextPosition(SwingConstants.RIGHT);
                button.setIconTextGap(10);
            }
        } catch (Exception e) {
            // If icon loading fails, just use text
            System.out.println("Could not load icon: " + iconPath);
        }
        
        // Ensure the button is opaque and content area is filled
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        
        // Force the button to show custom colors
        button.putClientProperty("JButton.buttonType", "normal");
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.DARK_GRAY);
                button.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.BLACK);
                button.repaint();
            }
        });
        
        return button;
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Set red background
        getContentPane().setBackground(new Color(153, 0, 0)); // Dark red
        
        // Top panel for logo and admin login
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(153, 0, 0));
        
        // Logo on the left
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(new Color(153, 0, 0));
        logoPanel.add(logoLabel);
        
        // Admin login on the right
        JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        adminPanel.setBackground(new Color(153, 0, 0));
        adminPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JButton adminLoginBtn = new JButton("ADMIN LOGIN");
        adminLoginBtn.setFont(new Font("Arial", Font.BOLD, 12));
        adminLoginBtn.setForeground(Color.WHITE);
        adminLoginBtn.setBackground(new Color(70, 70, 70));
        adminLoginBtn.setPreferredSize(new Dimension(150, 30));
        adminLoginBtn.setFocusPainted(false);
        adminLoginBtn.setBorderPainted(true);
        adminLoginBtn.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 1));
        adminLoginBtn.setOpaque(true);
        adminLoginBtn.setContentAreaFilled(true);
        
        // Add hover effect for admin button
        adminLoginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                adminLoginBtn.setBackground(new Color(100, 100, 100));
                adminLoginBtn.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                adminLoginBtn.setBackground(new Color(70, 70, 70));
                adminLoginBtn.repaint();
            }
        });
        
        // Admin login action
        adminLoginBtn.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new AdminLoginForm().setVisible(true));
        });
        
        adminPanel.add(adminLoginBtn);
        
        topPanel.add(logoPanel, BorderLayout.WEST);
        topPanel.add(adminPanel, BorderLayout.EAST);
        
        // Center panel for welcome and buttons
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(153, 0, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Welcome label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(50, 0, 50, 0);
        centerPanel.add(welcomeLabel, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonPanel.setBackground(new Color(153, 0, 0));
        buttonPanel.add(moviesBtn);
        buttonPanel.add(ticketsBtn);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 0, 100, 0);
        centerPanel.add(buttonPanel, gbc);
        
        // Bottom panel for social media info
        JPanel bottomPanel = createSocialMediaPanel();
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createSocialMediaPanel() {
        JPanel socialPanel = new JPanel(new BorderLayout());
        socialPanel.setBackground(Color.WHITE);
        socialPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // Follow us label
        JLabel followLabel = new JLabel("FOLLOW US:");
        followLabel.setFont(new Font("Arial", Font.BOLD, 24));
        followLabel.setForeground(Color.BLACK);
        
        // Social media info panel
        JPanel infoPanel = new JPanel(new GridLayout(2, 3, 20, 10));
        infoPanel.setBackground(Color.WHITE);
        
        // Social media entries
        infoPanel.add(createSocialLabel("Location: @TheCineMate", Color.RED));
        infoPanel.add(createSocialLabel("YouTube: @CinemateMovie_Reservation", Color.RED));
        infoPanel.add(createSocialLabel("Email: @Cinemate_Movie_Reservation.com", Color.BLACK));
        
        infoPanel.add(createSocialLabel("Facebook: Cinemate Movie Reservation", Color.BLUE));
        infoPanel.add(createSocialLabel("Twitter: @Cinemate_Movie_Reservation", Color.BLACK));
        infoPanel.add(createSocialLabel("Phone: 099-2123-932 / 123-456-094", Color.RED));
        
        socialPanel.add(followLabel, BorderLayout.WEST);
        socialPanel.add(infoPanel, BorderLayout.CENTER);
        
        return socialPanel;
    }
    
    private JLabel createSocialLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setForeground(color);
        return label;
    }
    
    private void setupEventHandlers() {
        moviesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MoviesForm().setVisible(true);
            }
        });
        
        ticketsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TicketsForm().setVisible(true);
            }
        });
    }
    
    private void setFrameProperties() {
        setTitle("The CineMate - Movie Ticket Reservation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null); // Center the window
        setResizable(true);
    }
    
    public static void main(String[] args) {
        // Set look and feel to Metal (cross-platform) for better custom styling
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenuForm().setVisible(true);
            }
        });
    }
} 