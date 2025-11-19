package com.bookstoreswing.ui.panels;

import com.bookstoreswing.service.FavoriteService;
import com.bookstoreswing.model.Book;
import com.bookstoreswing.ui.components.BookCardPanel;
import com.bookstoreswing.utils.ImageLoader;
import com.bookstoreswing.app.MainApp;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FavoritePanel extends JPanel {

    private Image bg;

    public FavoritePanel(FavoriteService favService) {

        setLayout(new BorderLayout());
        setOpaque(false);

        bg = ImageLoader.load("background/bg.jpg");

        JPanel listPanel = new JPanel();
        listPanel.setOpaque(false);
        listPanel.setLayout(new GridLayout(0, 3, 25, 25));

        List<Book> favs = favService.getAll();

        if (favs.isEmpty()) {
            listPanel.setLayout(new GridBagLayout());
            JLabel empty = new JLabel("No favorites yet");
            empty.setForeground(new Color(245, 230, 210));
            empty.setFont(new Font("Georgia", Font.BOLD, 28));
            listPanel.add(empty);
        } else {
            for (Book b : favs) {
                listPanel.add(new BookCardPanel(b, MainApp.CART));
            }
        }

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        scroll.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        add(scroll, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            g.setColor(new Color(20, 10, 10, 180));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
