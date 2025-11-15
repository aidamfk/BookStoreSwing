package com.bookstoreswing.ui.windows;

import javax.swing.*;
import java.awt.*;

public class FooterPanel extends JPanel {

    public FooterPanel() {
        setBackground(new Color(0x4A2E2B));
        setPreferredSize(new Dimension(0, 50));

        JLabel text = new JLabel("© 2025 Antiquarian Library — All rights reserved");
        text.setForeground(new Color(0xF5E9D0));
        text.setFont(new Font("SansSerif", Font.PLAIN, 14));

        add(text);
    }
}

