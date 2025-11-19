package com.bookstoreswing.app;

import javax.swing.SwingUtilities;

import com.bookstoreswing.service.CartService;
import com.bookstoreswing.service.FavoriteService;
import com.bookstoreswing.ui.windows.HomeWindow;

/**
 * Main application entry point.
 * Central place that stores shared services (Cart, Favorites, etc).
 */
public class MainApp {

    // 🔥 Shared CartService for entire application
    public static final CartService CART = new CartService();

    // 🔥 Shared FavoritesService
    public static final FavoriteService FAVORITES = new FavoriteService();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HomeWindow().setVisible(true);
        });
    }
}
