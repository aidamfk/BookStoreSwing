package com.bookstoreswing.ui.components;

import javax.swing.*;
import java.awt.*;

public class FooterPanel extends JPanel {

    public FooterPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(28, 40, 28, 40));

        JPanel left = new JPanel();
        left.setOpaque(false);
        JLabel brand = new JLabel("Antiquarian");
        brand.setFont(new Font("Georgia", Font.BOLD, 20));
        brand.setForeground(new Color(240,220,200));
        left.add(brand);

        JPanel center = new JPanel();
        center.setOpaque(false);
        JLabel info = new JLabel("<html><div style='text-align:center;'>Begin Your Journey Through Time<br>Join our community of collectors and literature enthusiasts.</div></html>");
        info.setForeground(new Color(220,200,180));
        center.add(info);

        add(left, BorderLayout.WEST);
        add(center, BorderLayout.CENTER);
    }
}
