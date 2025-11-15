package com.bookstoreswing.ui.windows;

import javax.swing.*;
import java.awt.*;

public class FavoritePanel extends JPanel {

    public FavoritePanel() {
        setLayout(new GridBagLayout());

        // 🔥 الخلفية التي أرسلتها أنت
        // ضع الصورة داخل مجلد resources
        setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;

        // Title
        JLabel title = new JLabel("Your Favorites");
        title.setFont(new Font("Serif", Font.BOLD, 32));
        title.setForeground(new Color(0xF5E9D0));
        title.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        add(title, gbc);

        // Subtext
        JLabel subtitle = new JLabel("0 books saved to your collection");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 18));
        subtitle.setForeground(new Color(0xD9C7A7));
        subtitle.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        add(subtitle, gbc);

        // Icon circle
        JPanel circle = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(255,255,255,40));
                g.fillOval(0,0,getWidth(),getHeight());
            }
        };
        circle.setPreferredSize(new Dimension(140, 140));
        circle.setOpaque(false);

        JLabel bookIcon = new JLabel("\uD83D\uDCD6");
        bookIcon.setFont(new Font("Serif", Font.PLAIN, 60));
        bookIcon.setForeground(new Color(0xF5E9D0));

        circle.add(bookIcon);
        add(circle, gbc);

        // No favorites text
        JLabel empty = new JLabel("No favorites yet");
        empty.setFont(new Font("Serif", Font.BOLD, 28));
        empty.setForeground(new Color(0xF5E9D0));
        empty.setBorder(BorderFactory.createEmptyBorder(20,0,10,0));
        add(empty, gbc);

        // Description
        JLabel desc = new JLabel("<html><center>Start building your personal collection<br>"
                + "by adding books to your favorites.<br>Click the heart icon on any book to save it here.</center></html>");
        desc.setFont(new Font("SansSerif", Font.PLAIN, 16));
        desc.setForeground(new Color(0xD9C7A7));
        desc.setBorder(BorderFactory.createEmptyBorder(10,0,20,0));
        add(desc, gbc);

        // Button
        JButton btn = new JButton("Browse Books");
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));
        btn.setBackground(new Color(0xD7C17A));
        btn.setForeground(new Color(0x4A2E2B));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btn.setFocusPainted(false);

        add(btn, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 🖼️ الخلفية (غيّر المسار حسب مكان وضع الصورة)
        Image bg = new ImageIcon("resources/library_bg.jpg").getImage();

        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(bg, 0, 0, getWidth(), getHeight(), this);

        // Overlay
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}


 



