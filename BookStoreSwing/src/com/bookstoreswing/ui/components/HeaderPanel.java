package com.bookstoreswing.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HeaderPanel extends JPanel {

    public HeaderPanel(String title) {

        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        setPreferredSize(new Dimension(0, 90));

        Color cream = new Color(240, 225, 205);
        Color hover = Color.WHITE;

        // LEFT — BRAND
        JLabel brand = new JLabel(title);
        brand.setFont(new Font("Georgia", Font.BOLD, 28));
        brand.setForeground(new Color(245, 230, 210));
        brand.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);
        left.add(brand);

        // CENTER — SEARCH BAR
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        center.setOpaque(false);

        JTextField searchField = new JTextField(32);
        searchField.setFont(new Font("Serif", Font.PLAIN, 16));
        searchField.setPreferredSize(new Dimension(380, 38));
        searchField.setMargin(new Insets(5, 10, 5, 10));

        JButton searchBtn = new JButton("\uD83D\uDD0D");
        searchBtn.setFont(new Font("Serif", Font.PLAIN, 18));
        searchBtn.setFocusPainted(false);
        searchBtn.setBackground(Color.WHITE);
        searchBtn.setPreferredSize(new Dimension(42, 38));

        center.add(searchField);
        center.add(searchBtn);

        // RIGHT — NAV BUTTONS
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        right.setOpaque(false);

        right.add(makeNavLabel("Home", cream, hover));
        right.add(makeNavLabel("Books", cream, hover));
        right.add(makeNavLabel("Favorite", cream, hover));
        right.add(makeNavLabel("Cart", cream, hover));

        add(left, BorderLayout.WEST);
        add(center, BorderLayout.CENTER);
        add(right, BorderLayout.EAST);
    }

    private JLabel makeNavLabel(String text, Color normal, Color hover) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Serif", Font.PLAIN, 18));
        lbl.setForeground(normal);
        lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lbl.setForeground(hover);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lbl.setForeground(normal);
            }
        });

        return lbl;
    }
}
