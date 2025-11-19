package com.bookstoreswing.ui.windows;

import javax.swing.*;
import java.awt.*;

import com.bookstoreswing.ui.components.HeaderPanel;
import com.bookstoreswing.ui.panels.BooksPanel;
import com.bookstoreswing.app.MainApp;
import com.bookstoreswing.utils.ImageLoader;

public class BookWindow extends JFrame {

    public BookWindow() {
        setTitle("Antiquarian - Books Collection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        Image bg = loadBackgroundImage();
        final Image bgFinal = bg;

        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (bgFinal != null) {
                    g.drawImage(bgFinal, 0, 0, getWidth(), getHeight(), this);
                    g.setColor(new Color(20, 10, 10, 180));
                    g.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    g.setColor(new Color(45, 35, 35));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        setContentPane(backgroundPanel);

        // NAVBAR
        HeaderPanel header = new HeaderPanel("Antiquarian");
        header.setActivePage("Books");
        backgroundPanel.add(header, BorderLayout.NORTH);

        // NAVIGATION
        header.addHomeListener(e -> {
            dispose();
            new HomeWindow().setVisible(true);
        });

        header.addBooksListener(e -> {});

        header.addFavoriteListener(e -> {
            new FavoriteWindow(MainApp.FAVORITES).setVisible(true);
            dispose();
        });

        header.addCartListener(e -> {
            new CartPage(MainApp.CART).setVisible(true);
            dispose();
        });

        // BOOK LIST PANEL
        BooksPanel booksPanel = new BooksPanel(MainApp.CART);
        backgroundPanel.add(booksPanel, BorderLayout.CENTER);
    }

    private Image loadBackgroundImage() {
        String[] paths = {
            "background/bg.jpg",
            "background/library.jpg"
        };

        for (String p : paths) {
            Image img = ImageLoader.loadImage(p);
            if (img != null) return img;
        }

        System.err.println("Background image NOT FOUND.");
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookWindow().setVisible(true));
    }
}
