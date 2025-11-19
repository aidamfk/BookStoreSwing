package com.bookstoreswing.utils;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Robust ImageLoader compatible with mixed call-sites across the project.
 *
 * Usage (all accepted):
 *   ImageLoader.loadImage("background/bg.jpg");
 *   ImageLoader.loadImage("/assets/background/bg.jpg");
 *   ImageLoader.load("assets/background/bg.jpg");            // alias
 *   ImageLoader.loadIcon("books/Fantasy1.jpg",200,260);
 *   ImageLoader.loadScaledIcon("/assets/books/Fantasy1.jpg",160,200); // alias
 *   ImageLoader.loadRaw("icons/heart.png");
 *
 * Notes:
 * - This loader assumes your resources are located under /assets/ on the runtime classpath.
 * - It normalizes messy paths (leading slash, duplicate "assets/" segments).
 */
public final class ImageLoader {

    private ImageLoader() {}

    // Normalize user path: remove leading slash(es), collapse duplicate assets, etc.
    private static String normalizePath(String path) {
        if (path == null) return "";
        path = path.trim();

        // remove leading slashes
        while (path.startsWith("/")) path = path.substring(1);

        // collapse repeated slashes
        path = path.replaceAll("/{2,}", "/");

        // if user already prefixed with assets/, remove it — we'll add exactly one later
        if (path.startsWith("assets/")) {
            path = path.substring("assets/".length());
        }

        return path;
    }

    // Build resource path used with Class.getResource
    private static String resourcePath(String userPath) {
        String p = normalizePath(userPath);
        return "/assets/" + p;
    }

    // ---------- RAW loader returning Image (for backgrounds, full images) ----------
    public static Image loadImage(String userPath) {
        try {
            String rp = resourcePath(userPath);
            URL u = ImageLoader.class.getResource(rp);
            if (u == null) {
                System.err.println("Image NOT FOUND: " + rp);
                return null;
            }
            return new ImageIcon(u).getImage();
        } catch (Exception e) {
            System.err.println("Error loading image '" + userPath + "': " + e.getMessage());
            return null;
        }
    }

    // alias : some files called load(...) before
    public static Image load(String userPath) {
        return loadImage(userPath);
    }

    // ---------- Load raw ImageIcon without scaling ----------
    public static ImageIcon loadRaw(String userPath) {
        try {
            String rp = resourcePath(userPath);
            URL u = ImageLoader.class.getResource(rp);
            if (u == null) {
                System.err.println("Raw icon NOT FOUND: " + rp);
                return null;
            }
            return new ImageIcon(u);
        } catch (Exception e) {
            System.err.println("Error loading raw icon '" + userPath + "': " + e.getMessage());
            return null;
        }
    }

    // ---------- Load scaled icon; returns ImageIcon ----------
    public static ImageIcon loadIcon(String userPath, int w, int h) {
        try {
            String rp = resourcePath(userPath);
            URL u = ImageLoader.class.getResource(rp);
            if (u == null) {
                System.err.println("Icon NOT FOUND: " + rp);
                return null;
            }
            Image img = new ImageIcon(u).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("Error loading icon '" + userPath + "': " + e.getMessage());
            return null;
        }
    }

    // alias for backward compatibility (your code referenced loadScaledIcon)
    public static ImageIcon loadScaledIcon(String userPath, int w, int h) {
        return loadIcon(userPath, w, h);
    }

    // alias for older code that might call loadIcon with leading slash double assets etc.
    public static ImageIcon loadIconSafe(String userPath, int w, int h) {
        return loadIcon(userPath, w, h);
    }
}
