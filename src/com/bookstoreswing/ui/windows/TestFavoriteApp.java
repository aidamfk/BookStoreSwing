package com.bookstoreswing.ui.windows;

import javax.swing.SwingUtilities;

public class TestFavoriteApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FavoriteWindow().setVisible(true);
            }
        });
    }
}


