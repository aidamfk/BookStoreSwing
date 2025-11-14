package com.bookstoreswing.ui.components;

import com.bookstoreswing.model.Book;
import com.bookstoreswing.service.CartService;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Book card with real image loading from assets
 */
public class BookCardPanel extends JPanel {

    public BookCardPanel(Book book, CartService cartService) {
        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(180, 320));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(140, 100, 95), 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        // COVER area with REAL image
        JPanel coverPanel = new JPanel();
        coverPanel.setOpaque(false);
        coverPanel.setPreferredSize(new Dimension(160, 200));
        coverPanel.setLayout(null);

        // Try to load actual book image
        ImageIcon bookImage = loadBookImage(book);
        JLabel imageLabel;

        if (bookImage != null) {
            // Scale image to fit
            Image scaledImage = bookImage.getImage().getScaledInstance(160, 200, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(scaledImage));
        } else {
            // Fallback: placeholder with book initials
            imageLabel = new JLabel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // background of cover
                    g.setColor(new Color(90, 60, 60));
                    g.fillRect(0, 0, getWidth(), getHeight());
                    // optional faux artwork: draw title initials
                    g.setColor(new Color(235, 220, 200));
                    g.setFont(new Font("Serif", Font.BOLD, 18));
                    String t = book.getTitle() == null ? "Book" : book.getTitle();
                    String initials = t.length() >= 2 ? t.substring(0, 2).toUpperCase() : t.toUpperCase();
                    FontMetrics fm = g.getFontMetrics();
                    int x = (getWidth() - fm.stringWidth(initials)) / 2;
                    int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                    g.drawString(initials, x, y);
                }
            };
        }

        imageLabel.setBounds(0, 0, 160, 200);
        coverPanel.add(imageLabel);

        // heart/favorite button (top-right)
        JButton heart = new JButton("\u2661");
        heart.setToolTipText("Add to favourites");
        heart.setBorderPainted(false);
        heart.setContentAreaFilled(false);
        heart.setForeground(new Color(220, 200, 180));
        heart.setBounds(120, 10, 30, 30);

        heart.addActionListener(e -> {
            heart.setText("\u2665");
            heart.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this, book.getTitle() + " added to favourites");
        });

        coverPanel.add(heart);
        add(coverPanel, BorderLayout.NORTH);

        // Title and author
        JPanel mid = new JPanel(new GridLayout(2, 1));
        mid.setOpaque(false);
        
        String displayTitle = book.getTitle();
        if (displayTitle.length() > 30) {
            displayTitle = displayTitle.substring(0, 27) + "...";
        }
        
        JLabel title = new JLabel("<html><div style='text-align:center;'>" + safeString(displayTitle) + "</div></html>");
        title.setFont(new Font("Serif", Font.BOLD, 13));
        title.setForeground(new Color(245, 235, 220));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        String displayAuthor = book.getAuthor();
        if (displayAuthor.length() > 25) {
            displayAuthor = displayAuthor.substring(0, 22) + "...";
        }
        
        JLabel author = new JLabel(safeString(displayAuthor), SwingConstants.CENTER);
        author.setFont(new Font("SansSerif", Font.PLAIN, 12));
        author.setForeground(new Color(210, 190, 170));

        mid.add(title);
        mid.add(author);
        mid.setBorder(BorderFactory.createEmptyBorder(8, 4, 8, 4));
        add(mid, BorderLayout.CENTER);

        // bottom: price + add-to-cart
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        
        String formattedPrice = formatPrice(book.getPrice() / 100.0);
        
        JLabel price = new JLabel(formattedPrice + " €");
        price.setFont(new Font("Serif", Font.BOLD, 13));
        price.setForeground(new Color(235, 215, 190));
        price.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));

        JButton add = new JButton("Add");
        add.setBackground(new Color(120, 78, 76));
        add.setForeground(Color.WHITE);
        add.setFocusPainted(false);
        add.addActionListener(e -> {
            if (cartService != null) {
                cartService.addBook(book);
                JOptionPane.showMessageDialog(this, book.getTitle() + " added to cart");
            } else {
                JOptionPane.showMessageDialog(this, "Cart service unavailable");
            }
        });

        bottom.add(price, BorderLayout.WEST);
        bottom.add(add, BorderLayout.EAST);
        bottom.setBorder(BorderFactory.createEmptyBorder(6, 4, 4, 4));
        add(bottom, BorderLayout.SOUTH);
    }

    private ImageIcon loadBookImage(Book book) {
        if (book.getImagePath() == null || book.getImagePath().isEmpty()) {
            return null;
        }
        
        try {
            String[] possiblePaths = {
                "/" + book.getImagePath(),
                "/src/" + book.getImagePath(),
                "/assets/" + book.getImagePath(),
                book.getImagePath(),
                "src/" + book.getImagePath(),
                "assets/" + book.getImagePath()
            };
            
            for (String path : possiblePaths) {
                java.net.URL imageUrl = getClass().getResource(path);
                if (imageUrl != null) {
                    System.out.println("Image trouvée: " + path);
                    return new ImageIcon(imageUrl);
                }
                
                File imageFile = new File(path);
                if (imageFile.exists()) {
                    System.out.println("Image trouvée (fichier): " + path);
                    return new ImageIcon(imageFile.getAbsolutePath());
                }
            }
            
            System.out.println("Image non trouvée pour: " + book.getImagePath());
            
        } catch (Exception e) {
            System.err.println("Error loading image for book: " + book.getTitle());
            e.printStackTrace();
        }
        
        return null;
    }

    private String safeString(String s) {
        return s == null ? "" : s;
    }

    private String formatPrice(double price) {
        return String.format("%.2f", price).replace(".", ",");
    }
}