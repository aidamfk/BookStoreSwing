package com.bookstoreswing.ui.windows;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {

    public HeaderPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(0x4A2E2B)); // Header background

        // LOGO
        JLabel logo = new JLabel("Antiquarian");
        logo.setForeground(new Color(0xF5E9D0));
        logo.setFont(new Font("Serif", Font.BOLD, 26));
        logo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // NAVIGATION
        JPanel nav = new JPanel();
        nav.setBackground(new Color(0x5B3A36));
        nav.setLayout(new FlowLayout(FlowLayout.RIGHT, 25, 12));

        nav.add(makeNavItem("Home"));
        nav.add(makeNavItem("Books"));
        nav.add(makeNavItem("Favorite"));
        nav.add(makeNavItem("Cart"));

        add(logo, BorderLayout.WEST);
        add(nav, BorderLayout.CENTER);
    }

    private JLabel makeNavItem(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(0xF2E2BE));
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        return label;
    }
}


