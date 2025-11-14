package com.bookstoreswing.ui.components;

import com.bookstoreswing.model.Book;
import com.bookstoreswing.service.CartService;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

/**
 * Card used in homepage featured grid — golden border, cover, title, price
 */
public class BookCardPanel extends JPanel {

    private final Book book;
    private final CartService cartService;

    public BookCardPanel(Book book, CartService cartService) {
        this.book = book;
        this.cartService = cartService;
        initUI();
    }

    private void initUI() {
        setOpaque(false);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(165, 320));
        setBorder(new EmptyBorder(6, 6, 6, 6));

        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // border
                g2.setColor(new Color(170,125,100,200));
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(4, 4, getWidth()-9, getHeight()-9, 8, 8);
                // translucent background
                g2.setColor(new Color(0,0,0,40));
                g2.fillRoundRect(5,5,getWidth()-10,getHeight()-10,8,8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // cover
        JLabel cover = new JLabel();
        cover.setHorizontalAlignment(SwingConstants.CENTER);
        cover.setPreferredSize(new Dimension(140, 200));
        cover.setIcon(loadCover(book.getImagePath(), 140, 200));
        card.add(cover, BorderLayout.NORTH);

        // title + author
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JLabel titleLbl = new JLabel("<html><center>" + safeTruncate(book.getTitle(), 50) + "</center></html>");
        titleLbl.setFont(new Font("Serif", Font.BOLD, 13));
        titleLbl.setForeground(new Color(245,235,230));
        titleLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel authorLbl = new JLabel(book.getAuthor());
        authorLbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        authorLbl.setForeground(new Color(210,190,170));
        authorLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        info.add(Box.createVerticalStrut(8));
        info.add(titleLbl);
        info.add(Box.createVerticalStrut(6));
        info.add(authorLbl);
        info.add(Box.createVerticalStrut(10));

        // price area
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        JLabel price = new JLabel(String.format("%.2f €", book.getPrice()));
        price.setFont(new Font("Serif", Font.BOLD, 13));
        price.setForeground(new Color(240,220,190));
        price.setBorder(BorderFactory.createEmptyBorder(0,6,6,6));
        bottom.add(price, BorderLayout.WEST);

        JButton add = new JButton("Add");
        add.setFocusPainted(false);
        add.setBackground(new Color(140,95,85));
        add.setForeground(new Color(245,235,230));
        add.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        add.addActionListener(e -> {
            cartService.addBook(book); // FIXED
            JOptionPane.showMessageDialog(this, "Added: " + book.getTitle());
        });
        bottom.add(add, BorderLayout.EAST);

        info.add(bottom);

        card.add(info, BorderLayout.CENTER);

        add(card, BorderLayout.CENTER);
    }

    private ImageIcon loadCover(String path, int w, int h) {
        try {
            // First try resource
            if (path != null && path.startsWith("/")) {
                URL u = getClass().getResource(path);
                if (u != null) {
                    Image img = ImageIO.read(u).getScaledInstance(w, h, Image.SCALE_SMOOTH);
                    return new ImageIcon(img);
                }
            }

            // Then try file in src/assets
            File f = new File("src/assets/" + path);
            if (f.exists()) {
                Image img = ImageIO.read(f).getScaledInstance(w, h, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            }

        } catch (Exception ignored) {}

        // placeholder
        BufferedImage ph = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics g = ph.getGraphics();
        g.setColor(new Color(160,160,160));
        g.fillRect(0,0,w,h);
        g.dispose();
        return new ImageIcon(ph);
    }

    private String safeTruncate(String s, int max) {
        if (s == null) return "";
        if (s.length() <= max) return s;
        return s.substring(0, max-3) + "...";
    }
}
