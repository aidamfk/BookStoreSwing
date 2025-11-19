package com.bookstoreswing.ui.windows;

import com.bookstoreswing.service.FavoriteService;
import com.bookstoreswing.app.MainApp;
import com.bookstoreswing.ui.components.HeaderPanel;
import com.bookstoreswing.ui.components.FooterPanel;
import com.bookstoreswing.ui.panels.FavoritePanel;
import com.bookstoreswing.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class FavoriteWindow extends JFrame {

    private final FavoriteService favService;
    private Image bg;

    public FavoriteWindow(FavoriteService favService) {
        this.favService = favService;

        setTitle("Your Favorites");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 720);
        setLocationRelativeTo(null);

        // Load background using ImageLoader
        bg = ImageLoader.loadImage("background/bg.jpg");

        JPanel root = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (bg != null) {
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);

                    g.setColor(new Color(20, 10, 10, 180));  // dark overlay
                    g.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    g.setColor(new Color(35, 30, 30));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        setContentPane(root);

        // HEADER
        HeaderPanel header = new HeaderPanel("Antiquarian");
        header.setActivePage("Favorite");

        header.addHomeListener(e -> {
            new HomeWindow().setVisible(true);
            dispose();
        });

        header.addBooksListener(e -> {
            new BookWindow().setVisible(true);
            dispose();
        });

        header.addCartListener(e -> {
            new CartPage(MainApp.CART).setVisible(true);
            dispose();
        });

        root.add(header, BorderLayout.NORTH);

        // FAVORITE LIST PANEL
        FavoritePanel panel = new FavoritePanel(favService);
        root.add(panel, BorderLayout.CENTER);

        // FOOTER
        root.add(new FooterPanel(), BorderLayout.SOUTH);
    }
}
