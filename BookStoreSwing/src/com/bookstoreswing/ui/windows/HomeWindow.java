package com.bookstoreswing.ui.windows;

import com.bookstoreswing.ui.components.BookCardPanel;
import com.bookstoreswing.ui.components.FooterPanel;
import com.bookstoreswing.ui.components.HeaderPanel;
import com.bookstoreswing.model.Book;
import com.bookstoreswing.service.BookService;
import com.bookstoreswing.service.CartService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.List;

public class HomeWindow extends JFrame {

    private CartService cartService;

    public HomeWindow() {
        setTitle("Antiquarian - Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 860);
        setLocationRelativeTo(null);

        Image bg = loadBackgroundImage();

        JPanel root = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bg != null) {
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(new Color(42, 20, 18, 160));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.dispose();
                } else {
                    g.setColor(new Color(35, 30, 30));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        root.setOpaque(false);
        setContentPane(root);

        // Header / Navbar
        HeaderPanel header = new HeaderPanel("Antiquarian");
        root.add(header, BorderLayout.NORTH);

        // MAIN SCROLL AREA
        JPanel main = new JPanel();
        main.setOpaque(false);
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        main.add(Box.createVerticalStrut(20));
        main.add(createHero());
        main.add(Box.createVerticalStrut(40));
        main.add(createFeatured());
        main.add(Box.createVerticalStrut(40));
        main.add(new FooterPanel());
        main.add(Box.createVerticalStrut(30));

        JScrollPane sc = new JScrollPane(main);
        sc.setOpaque(false);
        sc.getViewport().setOpaque(false);
        sc.setBorder(null);
        sc.getVerticalScrollBar().setUnitIncrement(16);
        sc.getVerticalScrollBar().setPreferredSize(new Dimension(10, Integer.MAX_VALUE));
        root.add(sc, BorderLayout.CENTER);
    }

    private Image loadBackgroundImage() {
        String[] resourceCandidates = {
            "/assets/bg.jpg", "/assets/bg.jpeg", "/assets/bg.png", "/assets/bg.jpg.jpg"
        };

        for (String r : resourceCandidates) {
            try {
                URL u = getClass().getResource(r);
                if (u != null) {
                    BufferedImage b = ImageIO.read(u);
                    if (b != null) return b;
                }
            } catch (Exception ignored) {}
        }

        String[] fileCandidates = {
            "src/assets/bg.jpg", "src/assets/bg.jpeg",
            "assets/bg.jpg", "assets/bg.jpeg"
        };

        for (String f : fileCandidates) {
            try {
                File ff = new File(f);
                if (ff.exists()) {
                    BufferedImage b = ImageIO.read(ff);
                    if (b != null) return b;
                }
            } catch (Exception ignored) {}
        }

        System.err.println("Background not found");
        return null;
    }

    private JPanel createHero() {
        JPanel hero = new JPanel();
        hero.setOpaque(false);
        hero.setLayout(new BoxLayout(hero, BoxLayout.Y_AXIS));
        hero.setBorder(BorderFactory.createEmptyBorder(110, 80, 110, 80));
        hero.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel big = new JLabel("Where History Meets Romance");
        big.setFont(new Font("Georgia", Font.BOLD, 64));
        big.setForeground(new Color(250, 240, 230));
        big.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("<html><div style='text-align:center;'>Discover rare treasures and timeless tales from centuries past.<br>Each book tells a story beyond its pages.</div></html>");
        sub.setFont(new Font("Serif", Font.PLAIN, 20));
        sub.setForeground(new Color(235, 220, 200));
        sub.setBorder(BorderFactory.createEmptyBorder(18, 0, 24, 0));
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton explore = new JButton("Explore books →");
        explore.setFont(new Font("Serif", Font.BOLD, 18));
        explore.setBackground(new Color(126, 80, 75));
        explore.setForeground(new Color(242, 225, 200));
        explore.setFocusPainted(false);
        explore.setPreferredSize(new Dimension(220, 44));
        explore.setMaximumSize(new Dimension(220, 44));
        explore.setAlignmentX(Component.CENTER_ALIGNMENT);
        explore.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        // FIXED LAMBDA — CORRECT BRACES ✔️
        explore.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    new BookWindow().setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Books window not available");
                }
            });
            dispose();
        });

        hero.add(big);
        hero.add(sub);
        hero.add(explore);

        return hero;
    }

    private JPanel createFeatured() {
        JPanel wrapper = new JPanel();
        wrapper.setOpaque(false);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        // Top row
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        JLabel title = new JLabel("Featured Treasures");
        title.setFont(new Font("Georgia", Font.BOLD, 28));
        title.setForeground(new Color(245, 230, 210));
        top.add(title, BorderLayout.WEST);

        JLabel viewAll = new JLabel("<html><u>View All →</u></html>");
        viewAll.setForeground(new Color(225, 200, 175));
        viewAll.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        top.add(viewAll, BorderLayout.EAST);
        wrapper.add(top);

        wrapper.add(Box.createVerticalStrut(12));

        JLabel small = new JLabel("Carefully curated selections from our collection");
        small.setFont(new Font("Serif", Font.PLAIN, 14));
        small.setForeground(new Color(215, 190, 170));
        small.setBorder(BorderFactory.createEmptyBorder(6, 0, 18, 0));
        wrapper.add(small);

        // Grid of 4
        JPanel grid = new JPanel(new GridLayout(1, 4, 28, 0));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        BookService bookService = new BookService();
        cartService = new CartService();
        List<Book> books = bookService.getAllBooks();
        int added = 0;
        for (Book b : books) {
            if (added >= 4) break;
            grid.add(new BookCardPanel(b, cartService));
            added++;
        }

        wrapper.add(grid);

        JLabel footerMsg = new JLabel("<html><div style='text-align:center;'>Begin Your Journey Through Time<br>Join our community of collectors and literature enthusiasts.</div></html>", SwingConstants.CENTER);
        footerMsg.setFont(new Font("Serif", Font.ITALIC, 15));
        footerMsg.setForeground(new Color(230,210,190));
        footerMsg.setBorder(BorderFactory.createEmptyBorder(26, 0, 0, 0));
        wrapper.add(footerMsg);

        return wrapper;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomeWindow().setVisible(true));
    }
}
