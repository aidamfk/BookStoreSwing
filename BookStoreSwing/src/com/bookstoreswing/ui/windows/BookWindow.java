package com.bookstoreswing.ui.windows;

import javax.swing.*;
import java.awt.*;
import com.bookstoreswing.ui.components.HeaderPanel;
import com.bookstoreswing.ui.panels.BooksPanel;
import com.bookstoreswing.service.CartService;

public class BookWindow extends JFrame {

    public BookWindow() {
        setTitle("Antiquarian - Books Collection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Load background image
        Image backgroundImage = null;
        try {
            java.net.URL u = getClass().getResource("/assets/bg.jpg");
            if (u == null) u = getClass().getResource("/assets/bg.jpg.jpg");
            if (u != null) backgroundImage = new ImageIcon(u).getImage();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final Image bgFinal = backgroundImage;

        // Background panel with dark overlay for better readability
        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgFinal != null) {
                    g.drawImage(bgFinal, 0, 0, getWidth(), getHeight(), this);
                    // Add dark overlay for better text readability
                    g.setColor(new Color(20, 10, 10, 180));
                    g.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    // Fallback background
                    g.setColor(new Color(45, 35, 35));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        setContentPane(backgroundPanel);

        // Add the navbar
        HeaderPanel header = new HeaderPanel("Antiquarian");
        backgroundPanel.add(header, BorderLayout.NORTH);

        // Create CartService and BooksPanel
        CartService cartService = new CartService();
        BooksPanel booksPanel = new BooksPanel(cartService);
        
        // Add BooksPanel to center
        backgroundPanel.add(booksPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookWindow().setVisible(true));
    }
}