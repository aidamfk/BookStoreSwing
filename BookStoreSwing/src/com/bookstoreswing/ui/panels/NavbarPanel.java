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
                    g2.setColor(new Color(12, 8, 6, 140));
                    g2.fillRect(0,0,getWidth(),getHeight());
                    g2.dispose();
                } else {
                    g.setColor(new Color(35,30,30));
                    g.fillRect(0,0,getWidth(),getHeight());
                }
            }
        };
        root.setOpaque(false);
        setContentPane(root);

        // header
        HeaderPanel header = new HeaderPanel("Antiquarian");
        root.add(header, BorderLayout.NORTH);

        // center scroll area
        JPanel main = new JPanel();
        main.setOpaque(false);
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        main.add(Box.createVerticalStrut(40));
        main.add(createHero());
        main.add(Box.createVerticalStrut(30));
        main.add(createFeatured());
        main.add(Box.createVerticalStrut(30));
        main.add(new FooterPanel());
        main.add(Box.createVerticalStrut(30));

        JScrollPane sc = new JScrollPane(main);
        sc.setOpaque(false);
        sc.getViewport().setOpaque(false);
        sc.setBorder(null);
        sc.getVerticalScrollBar().setUnitIncrement(16);
        root.add(sc, BorderLayout.CENTER);
    }

    private Image loadBackgroundImage() {
        String[] paths = {"/assets/bg.jpg", "/assets/bg.jpeg", "/assets/bg.png", "/assets/bg.jpg.jpg"};
        for (String p : paths) {
            try {
                URL u = getClass().getResource(p);
                if (u != null) {
                    BufferedImage b = ImageIO.read(u);
                    if (b != null) return b;
                }
            } catch (Exception ignored) {}
        }
        String[] files = {"src/assets/bg.jpg", "src/assets/bg.jpeg", "assets/bg.jpg", "assets/bg.jpeg"};
        for (String f : files) {
            try {
                File file = new File(f);
                if (file.exists()) {
                    BufferedImage b = ImageIO.read(file);
                    if (b != null) return b;
                }
            } catch (Exception ignored) {}
        }
        return null;
    }

    private JPanel createHero() {
        JPanel hero = new JPanel();
        hero.setOpaque(false);
        hero.setLayout(new BoxLayout(hero, BoxLayout.Y_AXIS));
        hero.setBorder(BorderFactory.createEmptyBorder(80, 80, 80, 80));
        hero.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel big = new JLabel("Where History Meets Romance");
        big.setFont(new Font("Georgia", Font.BOLD, 56));
        big.setForeground(new Color(250,240,230));
        big.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("<html><div style='text-align:center;'>Discover rare treasures and timeless tales from centuries past.<br>Each book tells a story beyond its pages.</div></html>");
        sub.setFont(new Font("Serif", Font.PLAIN, 20));
        sub.setForeground(new Color(235,220,200));
        sub.setBorder(BorderFactory.createEmptyBorder(18,0,24,0));
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton explore = new JButton("Explore Books →");
        explore.setFont(new Font("Serif", Font.BOLD, 20));
        explore.setBackground(new Color(150,110,90));
        explore.setForeground(new Color(250,240,230));
        explore.setFocusPainted(false);
        explore.setPreferredSize(new Dimension(260,56));
        explore.setMaximumSize(new Dimension(260,56));
        explore.setAlignmentX(Component.CENTER_ALIGNMENT);
        explore.addActionListener(e -> {
            // open books window (ensure BookWindow exists)
            SwingUtilities.invokeLater(() -> new BookWindow().setVisible(true));
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

        // title row
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        JLabel title = new JLabel("Featured Treasures");
        title.setFont(new Font("Georgia", Font.BOLD, 28));
        title.setForeground(new Color(245,230,210));
        top.add(title, BorderLayout.WEST);

        JLabel viewAll = new JLabel("<html><u>View All →</u></html>");
        viewAll.setForeground(new Color(225,200,175));
        viewAll.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        top.add(viewAll, BorderLayout.EAST);
        wrapper.add(top);
        wrapper.add(Box.createVerticalStrut(18));

        // grid
        JPanel grid = new JPanel(new GridLayout(1, 4, 20, 20));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        BookService bookService = new BookService();
        cartService = new CartService();
        java.util.List<Book> books = bookService.getAllBooks();
        int added = 0;
        for (Book b : books) {
            if (added >= 4) break;
            grid.add(new BookCardPanel(b, cartService));
            added++;
        }

        wrapper.add(grid);
        return wrapper;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomeWindow().setVisible(true));
    }
}
