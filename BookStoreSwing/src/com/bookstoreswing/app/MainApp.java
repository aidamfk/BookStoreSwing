package com.bookstoreswing.app;

import javax.swing.SwingUtilities;

import com.bookstoreswing.ui.windows.HomeWindow; // CORRECTION: windows au lieu de components

/**
 * Main application entry point
 */
public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HomeWindow().setVisible(true); // AJOUT: setVisible(true)
        });
    }
}