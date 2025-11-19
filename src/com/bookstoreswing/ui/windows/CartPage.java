package com.bookstoreswing.ui.windows;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

import com.bookstoreswing.service.CartService;
import com.bookstoreswing.model.CartItem;
import com.bookstoreswing.ui.components.HeaderPanel;
import com.bookstoreswing.app.MainApp;
import com.bookstoreswing.utils.ImageLoader;

public class CartPage extends JFrame {

    private CartService cartService;
    private JPanel listPanel;
    private JLabel totalLabel;
    private Image bgImage;
    private JLabel subtitleLabel;

    public CartPage(CartService cartService) {

        this.cartService = cartService;

        bgImage = ImageLoader.loadImage("background/bg.jpg");

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
                else {
                    g.setColor(new Color(35, 30, 30));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        setContentPane(root);

        HeaderPanel header = new HeaderPanel("Antiquarian");
        root.add(header, BorderLayout.NORTH);
        header.setActivePage("Cart");

        header.addHomeListener(e -> { new HomeWindow().setVisible(true); dispose(); });
        header.addBooksListener(e -> { new BookWindow().setVisible(true); dispose(); });
        header.addCartListener(e -> {});

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JLabel titleLabel = new JLabel("Your Cart");
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 34));
        titleLabel.setForeground(new Color(245, 230, 210));
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        subtitleLabel = new JLabel();
        subtitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(230, 210, 180));
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

        totalLabel = new JLabel("Total: 0,00 €");
        totalLabel.setFont(new Font("Georgia", Font.BOLD, 22));
        totalLabel.setForeground(new Color(250, 230, 180));
        bottom.add(totalLabel);

        JPanel centerBlock = new JPanel(new BorderLayout());
        centerBlock.setOpaque(false);
        centerBlock.add(headerPanel, BorderLayout.NORTH);
        centerBlock.add(scroll, BorderLayout.CENTER);

        root.add(centerBlock, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        refreshList();
    }

    private void refreshList() {
        listPanel.removeAll();

        List<CartItem> items = cartService.getItems();
        int totalQuantity = 0;
        for (CartItem it : items) totalQuantity += it.getQuantity();

        subtitleLabel.setText(totalQuantity + " items in your collection");


        if (items.isEmpty()) {
            JPanel emptyPanel = new JPanel();
            emptyPanel.setOpaque(false);
            emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
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
        } else {
            for (CartItem item : items) {
                listPanel.add(new BookItemPanel(item));
                listPanel.add(Box.createVerticalStrut(18));
            }
        }

        // update total
        double total = cartService.getTotal() / 100.0;
        totalLabel.setText(String.format("Total: %.2f €", total).replace('.', ','));

        listPanel.revalidate();
        listPanel.repaint();
    }

    // -----------------------------------------------------
    // BOOK ITEM PANEL
    // -----------------------------------------------------
    private class BookItemPanel extends JPanel {

        private CartItem cartItem;
        private JLabel qtyLabel;

        public BookItemPanel(CartItem item) {
            this.cartItem = item;

            setOpaque(false);
            setLayout(new BorderLayout(15, 0));
            setBorder(new LineBorder(new Color(200, 181, 122), 2, true));
            setPreferredSize(new Dimension(850, 170));

            // COVER IMAGE
            ImageIcon coverIcon = ImageLoader.loadIcon(item.getBook().getImagePath(), 120, 160);
            JLabel cover = new JLabel(coverIcon);
            add(cover, BorderLayout.WEST);

            // CENTER PANEL
            JPanel center = new JPanel();
            center.setOpaque(false);
            center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
            center.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel title = new JLabel(item.getBook().getTitle());
            title.setFont(new Font("Georgia", Font.BOLD, 18));
            title.setForeground(new Color(247, 223, 173));

            JLabel author = new JLabel(item.getBook().getAuthor());
            author.setForeground(new Color(240, 230, 210));

            JLabel price = new JLabel(String.format("%.2f €", item.getBook().getPrice() / 100.0).replace('.', ','));
            price.setForeground(new Color(240, 225, 190));
            price.setFont(new Font("Georgia", Font.PLAIN, 16));

            center.add(title);
            center.add(author);
            center.add(Box.createVerticalStrut(8));
            center.add(price);

            add(center, BorderLayout.CENTER);

            // RIGHT SIDE (QTY)
            JPanel right = new JPanel();
            right.setOpaque(false);
            right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
            right.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JButton minus  = new JButton(ImageLoader.loadScaledIcon("icons/minus.png", 22, 22));
            JButton plus   = new JButton(ImageLoader.loadScaledIcon("icons/plus.png", 22, 22));
            JButton remove = new JButton(ImageLoader.loadScaledIcon("icons/trash.png", 26, 26));

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

            // ---- FIXED LISTENERS (ONLY ONE EACH!) ----
            minus.addActionListener(e -> {
                int idx = cartService.getItems().indexOf(cartItem);
                cartService.decreaseQuantity(idx);
                refreshList();
            });

            plus.addActionListener(e -> {
                int idx = cartService.getItems().indexOf(cartItem);
                cartService.increaseQuantity(idx);
                refreshList();
            });

            remove.addActionListener(e -> {
                int idx = cartService.getItems().indexOf(cartItem);
                cartService.removeAt(idx);
                refreshList();
            });
        }
    }
}
