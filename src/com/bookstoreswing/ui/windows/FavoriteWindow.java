package com.bookstoreswing.ui.windows;

import javax.swing.*;
import java.awt.*;

public class FavoriteWindow extends JFrame {

    public FavoriteWindow() {
        setTitle("Favorite Books");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // HEADER
        add(new HeaderPanel(), BorderLayout.NORTH);

        // FAVORITE PANEL (CENTER)
        add(new FavoritePanel(), BorderLayout.CENTER);

        // FOOTER
        add(new FooterPanel(), BorderLayout.SOUTH);
    }
}



