package com.bookstoreswing.ui.panels;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import com.bookstoreswing.service.CartService; // AJOUT

public class AntiquarianApp extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentCards;
    private BufferedImage backgroundImage;
    private CartService cartService; // AJOUT

    public AntiquarianApp() {
        setTitle("Antiquarian - Bookstore");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // AJOUT: Initialiser le service panier
        cartService = new CartService();

        // Load background image (try resource then file)
        backgroundImage = loadBackgroundImage("/assets/bg.jpg", "src/assets/bg.jpg");

        // Top navbar (provides access to cardLayout+content)
        cardLayout = new CardLayout();
        contentCards = new JPanel(cardLayout);
        contentCards.setOpaque(false); // we'll paint BG in layered panel

        // Create pages - CORRECTION: passer cartService à BooksPanel
        HomePanel home = new HomePanel(() -> showCard("BOOKS"));
        BooksPanel books = new BooksPanel(cartService); // CORRECTION
        FavoritePanel fav = new FavoritePanel();
        CartPanel cart = new CartPanel();

        contentCards.add(home, "HOME");
        contentCards.add(books, "BOOKS");
        contentCards.add(fav, "FAVORITE");
        contentCards.add(cart, "CART");

        // Navbar receives a reference to the card container and layout
        NavbarPanel navbar = new NavbarPanel(cardLayout, contentCards);

        // Background + overlay layer: draw bg image and place navbar + content on top
        BackgroundLayer bgLayer = new BackgroundLayer(backgroundImage);
        bgLayer.setLayout(new BorderLayout());
        bgLayer.add(navbar, BorderLayout.NORTH);

        // Center area uses a transparent panel so cards show on top of background
        JPanel centerOverlay = new JPanel(new BorderLayout());
        centerOverlay.setOpaque(false);
        centerOverlay.add(contentCards, BorderLayout.CENTER);
        bgLayer.add(centerOverlay, BorderLayout.CENTER);

        add(bgLayer, BorderLayout.CENTER);

        // Start on HOME
        cardLayout.show(contentCards, "HOME");
    }

    private BufferedImage loadBackgroundImage(String resourcePath, String filePath) {
        try {
            // 1)  classpath (works if you imported assets into build path)
            java.net.URL url = getClass().getResource(resourcePath);
            if (url != null) {
                return ImageIO.read(url);
            }
            // 2) Fallback to file system path
            File f = new File(filePath);
            if (f.exists()) {
                return ImageIO.read(f);
            }
        } catch (Exception ex) {
            System.err.println("Background image load failed: " + ex.getMessage());
        }
        return null;
    }

    private void showCard(String name) {
        cardLayout.show(contentCards, name);
    }

    // Panel that paints the background image and a semi-transparent overlay
    private static class BackgroundLayer extends JPanel {
        private BufferedImage image;
        public BackgroundLayer(BufferedImage img) {
            this.image = img;
            setOpaque(true);
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                // draw scaled background
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                // dark overlay to improve readability
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(10, 10, 10, 140)); // semi-transparent dark
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            } else {
                // fallback: plain background
                g.setColor(new Color(245, 240, 230));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AntiquarianApp app = new AntiquarianApp();
            app.setVisible(true);
        });
    }
}