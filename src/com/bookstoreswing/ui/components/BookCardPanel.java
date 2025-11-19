package com.bookstoreswing.ui.components;

import com.bookstoreswing.model.Book;
import com.bookstoreswing.service.CartService;
import com.bookstoreswing.app.MainApp;
import com.bookstoreswing.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class BookCardPanel extends JPanel {

    public BookCardPanel(Book book, CartService cartService) {

        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(220, 360));   // bigger cards
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(140, 100, 95), 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        // ---------------- COVER IMAGE ----------------
        JPanel coverPanel = new JPanel(null);
        coverPanel.setOpaque(false);
        coverPanel.setPreferredSize(new Dimension(200, 260));

        // Auto prepend assets/ if missing
        String imgPath = book.getImagePath() == null ? "" : book.getImagePath();
        if (!imgPath.startsWith("assets/") && !imgPath.isEmpty()) {
            imgPath = "assets/" + imgPath;
        }

        ImageIcon coverIcon = ImageLoader.loadIcon(imgPath, 200, 260);
        JLabel imgLabel;

        if (coverIcon != null) {
            imgLabel = new JLabel(coverIcon);
        } else {
            imgLabel = new JLabel("?", SwingConstants.CENTER);
            imgLabel.setOpaque(true);
            imgLabel.setBackground(new Color(90, 60, 60));
            imgLabel.setForeground(Color.WHITE);
        }

        imgLabel.setBounds(0, 0, 200, 260);
        coverPanel.add(imgLabel);

        // ---------------- HEART ICON ----------------
        // NOTE: you named them `hearticon` and `heartfilled` — using exactly those names:
        ImageIcon emptyHeart = ImageLoader.loadIcon("assets/icons/hearticon.png", 30, 30);
        ImageIcon fullHeart  = ImageLoader.loadIcon("assets/icons/heartfilled.png", 30, 30);

        JButton heart;
        if (MainApp.FAVORITES.has(book) && fullHeart != null) {
            heart = new JButton(fullHeart);
        } else if (emptyHeart != null) {
            heart = new JButton(emptyHeart);
        } else {
            heart = new JButton("♡"); // Text fallback
        }

        heart.setBorderPainted(false);
        heart.setContentAreaFilled(false);
        heart.setFocusPainted(false);
        heart.setBounds(155, 10, 30, 30);

        heart.addActionListener(e -> {
            MainApp.FAVORITES.add(book);
            if (fullHeart != null) heart.setIcon(fullHeart);
            heart.setEnabled(false);
            JOptionPane.showMessageDialog(this, book.getTitle() + " added to favorites");
        });

        if (MainApp.FAVORITES.has(book)) {
            heart.setEnabled(false);
        }

        coverPanel.add(heart);
        add(coverPanel, BorderLayout.NORTH);

        // ---------------- TITLE + AUTHOR ----------------
        JPanel mid = new JPanel(new GridLayout(2, 1));
        mid.setOpaque(false);

        JLabel title = new JLabel("<html><center>" + book.getTitle() + "</center></html>");
        title.setForeground(new Color(245, 235, 220));
        title.setFont(new Font("Serif", Font.BOLD, 14));

        JLabel author = new JLabel(book.getAuthor(), SwingConstants.CENTER);
        author.setForeground(new Color(210, 190, 170));
        author.setFont(new Font("SansSerif", Font.PLAIN, 13));

        mid.add(title);
        mid.add(author);
        add(mid, BorderLayout.CENTER);

        // ---------------- PRICE + ADD BUTTON ----------------
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);

        JLabel price = new JLabel(String.format("%.2f €", book.getPrice() / 100.0).replace('.', ','));
        price.setForeground(new Color(240, 220, 190));
        price.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        price.setFont(new Font("Serif", Font.BOLD, 14));

        JButton addBtn = new JButton("Add");
        addBtn.setBackground(new Color(120, 78, 76));
        addBtn.setForeground(Color.WHITE);

        addBtn.addActionListener(e -> {
            cartService.addBook(book);
            JOptionPane.showMessageDialog(this, book.getTitle() + " added to cart");
        });

        bottom.add(price, BorderLayout.WEST);
        bottom.add(addBtn, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);
    }
}
