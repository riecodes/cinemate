package com.mycompany.cinemate.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class MovieDetailsForm extends JFrame {
    private String movieTitle;
    private String movieImagePath;
    private String movieDescription;
    private String showingStart;
    private String showingEnd;
    private String price;
    private JLabel logoLabel;
    private JLabel posterLabel;
    private JButton backButton;
    
    public MovieDetailsForm(String title, String imagePath) {
        this.movieTitle = title;
        this.movieImagePath = imagePath;
        
        // Set default movie data based on title
        setMovieData();
        
        initializeComponents();
        setupLayout();
        setFrameProperties();
    }
    
    private void setMovieData() {
        // Official movie data from MOVIE DESCRIPTIONS.pdf
        switch (movieTitle) {
            case "How To Train Your Dragon":
                movieDescription = "A live-action adaptation of the beloved animated classic, this film follows the unlikely friendship between a young Viking named Hiccup and a dragon named Toothless. Together, they challenge long-standing myths and change their world forever.";
                showingStart = "June 01, 2025";
                showingEnd = "June 10, 2025";
                price = "₱290";
                break;
            case "Megan 2.0":
                movieDescription = "The terrifying AI doll returns in this chilling sequel. After the events of the first film, M3GAN is reactivated—smarter, deadlier, and harder to control. With her new programming evolving beyond human understanding, no one is safe.";
                showingStart = "June 03, 2025";
                showingEnd = "June 27, 2025";
                price = "₱310";
                break;
            case "Lilo & Stitch":
                movieDescription = "This heartfelt live-action remake reintroduces the story of a lonely Hawaiian girl named Lilo who adopts a mysterious alien creature, Stitch. What begins as chaos transforms into a beautiful lesson about family, love, and ohana.";
                showingStart = "June 03, 2025";
                showingEnd = "June 27, 2025";
                price = "₱250";
                break;
            case "Ballerina":
                movieDescription = "Set in the high-octane John Wick universe, Ballerina follows Rooney (Ana de Armas), a deadly assassin trained in ballet, as she seeks revenge for her family's murder. Graceful yet brutal, her journey uncovers deep secrets and powerful enemies.";
                showingStart = "June 03, 2025";
                showingEnd = "June 27, 2025";
                price = "₱360";
                break;
            case "Elio":
                movieDescription = "An imaginative boy named Elio is accidentally beamed into space and mistakenly appointed as Earth's ambassador to a galaxy full of aliens. A charming coming-of-age story from Pixar about identity, bravery, and finding one's voice in a strange universe.";
                showingStart = "June 03, 2025";
                showingEnd = "June 27, 2025";
                price = "₱230";
                break;
            case "Pelikulaya: Young Hearts":
                movieDescription = "A Filipino youth-centered film showcasing diverse love stories and modern struggles, set in a vibrant campus environment. This entry in the Pelikulaya series captures the joys and heartbreaks of young adulthood with authenticity and cultural flair.";
                showingStart = "June 03, 2025";
                showingEnd = "June 27, 2025";
                price = "₱250";
                break;
            case "Only We Know":
                movieDescription = "A romantic mystery that follows two soulmates who keep finding each other in different lifetimes. As they uncover pieces of their pasts, they realize some love stories are destined to repeat—until they finally get it right.";
                showingStart = "June 03, 2025";
                showingEnd = "June 27, 2025";
                price = "₱200";
                break;
            case "Unconditional":
                movieDescription = "A heart-tugging drama about the unbreakable bond between a mother and her child. As life tests their limits, they discover that true love is not based on conditions—but on sacrifice, understanding, and unwavering support.";
                showingStart = "June 03, 2025";
                showingEnd = "June 27, 2025";
                price = "₱200";
                break;
            case "28 Years Later":
                movieDescription = "The long-awaited sequel to 28 Days Later and 28 Weeks Later. The rage virus resurfaces, more evolved and deadlier than ever. Survivors must navigate a ruined world as civilization teeters on the edge of total collapse.";
                showingStart = "June 03, 2025";
                showingEnd = "June 27, 2025";
                price = "₱200";
                break;
            case "The Ritual":
                movieDescription = "A psychological horror film that follows a group of friends who become lost in a Scandinavian forest, where they encounter ancient and terrifying forces. As paranoia sets in and the group begins to fracture, they must face both supernatural threats and their own darkest fears.";
                showingStart = "June 03, 2025";
                showingEnd = "June 27, 2025";
                price = "₱200";
                break;
            default:
                movieDescription = "An amazing cinematic experience that will captivate audiences of all ages. Don't miss this incredible film that promises to deliver entertainment, emotion, and unforgettable moments.";
                showingStart = "Coming Soon";
                showingEnd = "TBA";
                price = "₱280";
                break;
        }
    }
    
    private void initializeComponents() {
        createLogoLabel();
        createPosterLabel();
        createButtons();
    }
    
    private void createLogoLabel() {
        logoLabel = new JLabel();
        try {
            URL logoUrl = getClass().getResource("/assets/logo.png");
            if (logoUrl != null) {
                ImageIcon logoIcon = new ImageIcon(logoUrl);
                Image img = logoIcon.getImage();
                Image scaledImg = img.getScaledInstance(200, 100, Image.SCALE_SMOOTH);
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
    
    private void createPosterLabel() {
        posterLabel = new JLabel();
        posterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        posterLabel.setVerticalAlignment(SwingConstants.CENTER);
        posterLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 3), // Gold border
            BorderFactory.createLineBorder(Color.BLACK, 2)
        ));
        
        try {
            URL posterUrl = getClass().getResource(movieImagePath);
            if (posterUrl != null) {
                ImageIcon posterIcon = new ImageIcon(posterUrl);
                Image img = posterIcon.getImage();
                Image scaledImg = img.getScaledInstance(300, 400, Image.SCALE_SMOOTH);
                posterLabel.setIcon(new ImageIcon(scaledImg));
            } else {
                posterLabel.setText("NO IMAGE");
                posterLabel.setForeground(Color.WHITE);
                posterLabel.setFont(new Font("Arial", Font.BOLD, 16));
                posterLabel.setPreferredSize(new Dimension(300, 400));
            }
        } catch (Exception e) {
            posterLabel.setText("NO IMAGE");
            posterLabel.setForeground(Color.WHITE);
            posterLabel.setFont(new Font("Arial", Font.BOLD, 16));
            posterLabel.setPreferredSize(new Dimension(300, 400));
        }
    }
    
    private void createButtons() {
        // Back button
        backButton = new JButton("BACK");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(Color.BLACK);
        backButton.setPreferredSize(new Dimension(120, 50));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(true);
        backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        backButton.setOpaque(true);
        backButton.setContentAreaFilled(true);
        
        // Add hover effects
        addHoverEffect(backButton);
        
        // Action listeners
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        

    }
    
    private void addHoverEffect(JButton button) {
        Color originalBackground = button.getBackground();
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(60, 60, 60));
                button.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalBackground);
                button.repaint();
            }
        });
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(139, 0, 0)); // Dark red background
        
        // Top panel with title and logo
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(139, 0, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 15, 40));
        
        JLabel titleLabel = new JLabel("//SEE DETAILS (BUTTON) FORM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        
        // Logo section with background
        JPanel logoSection = new JPanel(new BorderLayout());
        logoSection.setBackground(new Color(180, 0, 0, 100)); // Semi-transparent background
        logoSection.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        logoSection.setPreferredSize(new Dimension(220, 120));
        logoSection.add(logoLabel, BorderLayout.CENTER);
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(logoSection, BorderLayout.EAST);
        
        // Center panel with movie details
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(139, 0, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
        
        // Left side - Movie poster
        JPanel posterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        posterPanel.setBackground(new Color(139, 0, 0));
        posterPanel.add(posterLabel);
        
        // Right side - Movie information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(139, 0, 0));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 20));
        
        // Movie title
        JLabel movieTitleLabel = new JLabel(movieTitle.toUpperCase());
        movieTitleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        movieTitleLabel.setForeground(Color.WHITE);
        movieTitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Description
        JLabel descLabel = new JLabel("DESCRIPTION:");
        descLabel.setFont(new Font("Arial", Font.BOLD, 16));
        descLabel.setForeground(Color.WHITE);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextArea descText = new JTextArea(movieDescription);
        descText.setFont(new Font("Arial", Font.PLAIN, 14));
        descText.setForeground(Color.WHITE);
        descText.setBackground(new Color(139, 0, 0));
        descText.setLineWrap(true);
        descText.setWrapStyleWord(true);
        descText.setEditable(false);
        descText.setOpaque(false);
        descText.setAlignmentX(Component.LEFT_ALIGNMENT);
        descText.setPreferredSize(new Dimension(500, 80));
        descText.setMaximumSize(new Dimension(600, 100));
        
        // Showing dates and price
        JLabel showingStartLabel = new JLabel("SHOWING START:");
        showingStartLabel.setFont(new Font("Arial", Font.BOLD, 16));
        showingStartLabel.setForeground(Color.WHITE);
        showingStartLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel startDateLabel = new JLabel(showingStart);
        startDateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        startDateLabel.setForeground(Color.WHITE);
        startDateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel showingEndLabel = new JLabel("SHOWING ENDS:");
        showingEndLabel.setFont(new Font("Arial", Font.BOLD, 16));
        showingEndLabel.setForeground(Color.WHITE);
        showingEndLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel endDateLabel = new JLabel(showingEnd);
        endDateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        endDateLabel.setForeground(Color.WHITE);
        endDateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel priceLabel = new JLabel("PRICE:");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        priceLabel.setForeground(Color.WHITE);
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel priceValueLabel = new JLabel(price);
        priceValueLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        priceValueLabel.setForeground(Color.WHITE);
        priceValueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Add components to info panel
        infoPanel.add(movieTitleLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        infoPanel.add(descLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(descText);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        infoPanel.add(showingStartLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(startDateLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        infoPanel.add(showingEndLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(endDateLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        infoPanel.add(priceLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(priceValueLabel);
        
        centerPanel.add(posterPanel, BorderLayout.WEST);
        centerPanel.add(infoPanel, BorderLayout.CENTER);
        
        // Bottom panel with buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        bottomPanel.setBackground(new Color(139, 0, 0));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 40));
        bottomPanel.add(backButton);
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setFrameProperties() {
        setTitle("The CineMate - Movie Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1400, 900);
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
                new MovieDetailsForm("How To Train Your Dragon", "/assets/how to train your dragon poster.png").setVisible(true);
            }
        });
    }
} 