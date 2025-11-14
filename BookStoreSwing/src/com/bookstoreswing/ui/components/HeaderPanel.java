package com.bookstoreswing.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Header with logo, search bar, and navigation menu similar to design
 */
public class HeaderPanel extends JPanel {
    private JButton homeBtn, booksBtn, favoriteBtn, cartBtn;
    private String currentPage;

    public HeaderPanel(String title) {
        setLayout(new BorderLayout());
        setBackground(new Color(70, 50, 50)); // dark maroon/brown
        setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        setPreferredSize(new Dimension(getWidth(), 70));

        // Left: Logo with icon
        JPanel leftPanel = createLogoPanel(title);
        add(leftPanel, BorderLayout.WEST);

        // Center: Search bar with search icon
        JPanel centerPanel = createSearchPanel();
        add(centerPanel, BorderLayout.CENTER);

        // Right: Navigation menu with icons
        JPanel rightPanel = createNavigationPanel();
        add(rightPanel, BorderLayout.EAST);

        // Set default active page
        setActivePage("Books");
    }

    private JPanel createLogoPanel(String title) {
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setOpaque(false);

        // Icon (using text icon for now)
        JLabel iconLabel = new JLabel("📚");
        iconLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(246, 236, 220));

        logoPanel.add(iconLabel);
        logoPanel.add(titleLabel);

        return logoPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setOpaque(false);

        // Search field with rounded appearance
        JTextField searchField = new JTextField(25);
        searchField.setPreferredSize(new Dimension(400, 38));
        searchField.setText("Search for book, author, theme");
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setForeground(Color.GRAY);

        // Search icon button
        JButton searchButton = new JButton("🔍");
        searchButton.setFont(new Font("Serif", Font.PLAIN, 16));
        searchButton.setPreferredSize(new Dimension(40, 38));
        searchButton.setBackground(new Color(100, 80, 80));
        searchButton.setForeground(new Color(246, 236, 220));
        searchButton.setBorder(BorderFactory.createEmptyBorder());
        searchButton.setFocusPainted(false);

        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(searchButton);

        return searchPanel;
    }

    private JPanel createNavigationPanel() {
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        navPanel.setOpaque(false);

        // Create navigation buttons
        homeBtn = createNavButton("🏠 Home", "Home");
        booksBtn = createNavButton("📖 Books", "Books");
        favoriteBtn = createNavButton("❤️ Favorite", "Favorite");
        cartBtn = createNavButton("🛒 Cart", "Cart");

        navPanel.add(homeBtn);
        navPanel.add(booksBtn);
        navPanel.add(favoriteBtn);
        navPanel.add(cartBtn);

        return navPanel;
    }

    private JButton createNavButton(String text, String page) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setForeground(new Color(246, 236, 220));
        button.setBackground(new Color(70, 50, 50));
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(90, 70, 70));
                button.setContentAreaFilled(true);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!button.getText().contains("●")) {
                    button.setBackground(new Color(70, 50, 50));
                    button.setContentAreaFilled(false);
                }
            }
        });

        button.addActionListener(e -> setActivePage(page));

        return button;
    }

    public void setActivePage(String page) {
        this.currentPage = page;
        
        // Reset all buttons
        resetButton(homeBtn, "🏠 Home");
        resetButton(booksBtn, "📖 Books");
        resetButton(favoriteBtn, "❤️ Favorite");
        resetButton(cartBtn, "🛒 Cart");
        
        // Highlight active button
        JButton activeButton = null;
        switch (page) {
            case "Home": activeButton = homeBtn; break;
            case "Books": activeButton = booksBtn; break;
            case "Favorite": activeButton = favoriteBtn; break;
            case "Cart": activeButton = cartBtn; break;
        }
        
        if (activeButton != null) {
            activeButton.setText("● " + activeButton.getText().replace("● ", ""));
            activeButton.setBackground(new Color(120, 90, 90));
            activeButton.setContentAreaFilled(true);
            activeButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(246, 236, 220), 1),
                BorderFactory.createEmptyBorder(7, 11, 7, 11)
            ));
        }
    }

    private void resetButton(JButton button, String originalText) {
        button.setText(originalText);
        button.setBackground(new Color(70, 50, 50));
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
    }

    // Methods to add external action listeners
    public void addHomeListener(ActionListener listener) {
        homeBtn.addActionListener(listener);
    }

    public void addBooksListener(ActionListener listener) {
        booksBtn.addActionListener(listener);
    }

    public void addFavoriteListener(ActionListener listener) {
        favoriteBtn.addActionListener(listener);
    }

    public void addCartListener(ActionListener listener) {
        cartBtn.addActionListener(listener);
    }

    public String getCurrentPage() {
        return currentPage;
    }
}