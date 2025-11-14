package com.bookstoreswing.ui.windows;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import com.bookstoreswing.service.CartService;
import com.bookstoreswing.model.CartItem;
import com.bookstoreswing.model.Book;
import java.util.List;

public class CartPage extends JFrame {

    private CartService cartService;
    private JPanel listPanel;
    private JLabel totalLabel;
    private Image bgImage;
    private JLabel subtitleLabel;


    public CartPage(CartService cartService) {
        this.cartService = cartService;

        bgImage = loadImage("/assets/bg.jpg.jpg");

        setTitle("Your Cart");
        setSize(1100, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null)
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
        setContentPane(root);

        JPanel nav = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        nav.setOpaque(false);

        JLabel brand = new JLabel("📚 Antiquarian");
        brand.setFont(new Font("Georgia", Font.BOLD, 22));
        brand.setForeground(new Color(247, 223, 173));

        nav.add(brand);
        root.add(nav, BorderLayout.NORTH);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JLabel header = new JLabel("Your Cart");
        header.setFont(new Font("Georgia", Font.BOLD, 34));
        header.setForeground(new Color(245, 230, 210));

        subtitleLabel = new JLabel();
        subtitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(230, 210, 180));

        headerPanel.add(header, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        listPanel = new JPanel();
        listPanel.setOpaque(false);
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);

        totalLabel = new JLabel("Total: 0.00 €");
        totalLabel.setFont(new Font("Georgia", Font.BOLD, 22));
        totalLabel.setForeground(new Color(250, 230, 180));

        bottom.add(totalLabel);

        JPanel centerBlock = new JPanel(new BorderLayout());
        centerBlock.setOpaque(false);
        centerBlock.add(headerPanel, BorderLayout.NORTH);
        centerBlock.add(scroll, BorderLayout.CENTER);

        root.add(centerBlock, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        refreshList();  // ✔️ fixed call
    }


         private void refreshList() {

        listPanel.removeAll();

        List<CartItem> items = cartService.getItems();

        // Update subtitle
        subtitleLabel.setText(items.size() + " items in your collection");

        if (items.isEmpty()) {

            JPanel emptyPanel = new JPanel();
            emptyPanel.setOpaque(false);
            emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
            emptyPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            emptyPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));

            JLabel emptyMsg = new JLabel("Your cart is empty");
            emptyMsg.setFont(new Font("Georgia", Font.BOLD, 28));
            emptyMsg.setForeground(new Color(245, 230, 210));
            emptyMsg.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel exploreMsg = new JLabel("Explore our collection of antique and rare books to discover literary treasures");
            exploreMsg.setFont(new Font("Georgia", Font.PLAIN, 18));
            exploreMsg.setForeground(new Color(240, 220, 190));
            exploreMsg.setAlignmentX(Component.CENTER_ALIGNMENT);

            emptyPanel.add(emptyMsg);
            emptyPanel.add(Box.createVerticalStrut(15));
            emptyPanel.add(exploreMsg);

            listPanel.add(emptyPanel);
        }

         else {
            for (CartItem item : items) {
                listPanel.add(new BookItemPanel(item));
                listPanel.add(Box.createVerticalStrut(18));
            }
        }

        totalLabel.setText(String.format("Total: %.2f €", cartService.getTotal()));

        listPanel.revalidate();
        listPanel.repaint();
    }


    //
    // ---------------- BOOK ITEM PANEL ----------------
    //
    private class BookItemPanel extends JPanel {

        private CartItem cartItem;
        private JLabel qtyLabel;

        public BookItemPanel(CartItem item) {
            this.cartItem = item;

            setOpaque(false);
            setLayout(new BorderLayout(15, 0));
            setBorder(new LineBorder(new Color(200, 181, 122), 2, true));
            setPreferredSize(new Dimension(850, 170));
            setMaximumSize(new Dimension(900, 170));
            setMinimumSize(new Dimension(850, 170));
            setBackground(new Color(0, 0, 0, 40));

            // COVER IMAGE
            ImageIcon coverIcon = loadScaledIcon("/assets/images/book" + item.getBook().getId() + ".jpg", 120, 160);
            JLabel cover = new JLabel(coverIcon);

            add(cover, BorderLayout.WEST);

            // CENTER TEXT
            JPanel center = new JPanel();
            center.setOpaque(false);
            center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
            center.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel title = new JLabel(item.getBook().getTitle());
            title.setFont(new Font("Georgia", Font.BOLD, 18));
            title.setForeground(new Color(247, 223, 173));

            JLabel author = new JLabel(item.getBook().getAuthor());
            author.setForeground(new Color(240, 230, 210));

            JLabel price = new JLabel(String.format("%.2f €", item.getBook().getPrice()));
            price.setForeground(new Color(240, 225, 190));
            price.setFont(new Font("Georgia", Font.PLAIN, 16));

            center.add(title);
            center.add(author);
            center.add(Box.createVerticalStrut(8));
            center.add(price);

            add(center, BorderLayout.CENTER);

            // RIGHT SIDE
            JPanel right = new JPanel();
            right.setOpaque(false);
            right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
            right.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // FIXED ICON PATHS
            JButton minus = new JButton(loadScaledIcon("/assets/icons/minus.png", 22, 22));
            JButton plus = new JButton(loadScaledIcon("/assets/icons/plus.png", 22, 22));
            JButton remove = new JButton(loadScaledIcon("/assets/icons/trash..png", 26, 26));

            // button visuals
            for (JButton b : new JButton[]{minus, plus, remove}) {
                b.setContentAreaFilled(false);
                b.setBorder(null);
                b.setFocusPainted(false);
            }

            qtyLabel = new JLabel(String.valueOf(item.getQuantity()));
            qtyLabel.setForeground(new Color(250, 230, 200));
            qtyLabel.setFont(new Font("Georgia", Font.BOLD, 16));

            JPanel qtyPanel = new JPanel();
            qtyPanel.setOpaque(false);
            qtyPanel.add(minus);
            qtyPanel.add(qtyLabel);
            qtyPanel.add(plus);

            right.add(remove);
            right.add(Box.createVerticalStrut(20));
            right.add(qtyPanel);

            add(right, BorderLayout.EAST);

            // Actions
            minus.addActionListener(e -> {
                cartService.decreaseQuantity(cartService.getItems().indexOf(cartItem));
                refreshList();
            });


            plus.addActionListener(e -> {
                cartService.increaseQuantity(cartService.getItems().indexOf(cartItem));
                refreshList();
            });

            remove.addActionListener(e -> {
                cartService.removeAt(cartService.getItems().indexOf(cartItem));
                refreshList();
            });

            remove.addActionListener(e -> {
                cartService.removeAt(cartService.getItems().indexOf(cartItem));
                refreshList();
            });
        }
    }

    // ---------------- IMAGE HELPERS -------------------

    private Image loadImage(String path) {
        try { return new ImageIcon(getClass().getResource(path)).getImage(); }
        catch (Exception e) { System.err.println("Missing bg: " + path); return null; }
    }

    private ImageIcon loadScaledIcon(String path, int w, int h) {
        try {
            Image img = new ImageIcon(getClass().getResource(path)).getImage()
                    .getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("Missing icon: " + path);
            return null;
        }
    }

    // TEST MAIN
    public static void main(String[] args) {
        CartService cartService = new CartService();
        cartService.addBook(new Book("01", "Le langage de la nuit", "Ursula K. Le Guin", 14.80, ""));
        cartService.addBook(new Book("2", "Un passé englouti", "Ava Reid", 27.99, ""));
        cartService.addBook(new Book("3", "Outlander, Tome 2 : Le Talisman", "Diana Gabaldon", 18.90, ""));

        SwingUtilities.invokeLater(() -> new CartPage(cartService).setVisible(true));
    }
}
