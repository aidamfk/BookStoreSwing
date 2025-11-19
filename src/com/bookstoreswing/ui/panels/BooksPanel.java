package com.bookstoreswing.ui.panels;

import com.bookstoreswing.model.Book;
import com.bookstoreswing.service.BookService;
import com.bookstoreswing.service.CartService;
import com.bookstoreswing.ui.components.BookCardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BooksPanel extends JPanel {

    private final BookService bookService;
    private final CartService cartService;
    private JPanel booksGridPanel;
    private JComboBox<String> categoryComboBox;

    public BooksPanel(CartService cartService) {
        this.bookService = new BookService();
        this.cartService = cartService;
        initializeUI();
    }

    private void initializeUI() {
        setOpaque(false);
        setLayout(new BorderLayout());

        add(createHeaderPanel(), BorderLayout.NORTH);

        booksGridPanel = new JPanel();
        booksGridPanel.setLayout(new GridLayout(0, 3, 20, 20));
        booksGridPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(booksGridPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        loadBooks("All books");
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        JLabel titleLabel = new JLabel("Our Collection");
        titleLabel.setForeground(new Color(245, 235, 220));
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));

        JLabel subtitleLabel = new JLabel("Browse through our extensive collection of rare and vintage literature");
        subtitleLabel.setForeground(new Color(210, 190, 170));
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JPanel filterPanel = new JPanel(new BorderLayout(15, 0));
        filterPanel.setOpaque(false);

        JTextField searchField = new JTextField("Search by title or authors...");
        searchField.setForeground(new Color(70, 50, 40));
        searchField.setBackground(new Color(230, 210, 190));
        searchField.setCaretColor(new Color(70, 50, 40));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 180, 160), 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JButton searchButton = new JButton("🔍");
        searchButton.setPreferredSize(new Dimension(45, 35));
        searchButton.setBackground(new Color(180, 150, 130));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 160), 1));
        searchButton.setFocusPainted(false);

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        categoryComboBox = new JComboBox<>();
        String[] allCategories = {"All books", "Romance", "Historical", "Fantasy", "Guerre"};

        for (String category : allCategories) {
            categoryComboBox.addItem(category);
        }

        categoryComboBox.setBackground(new Color(230, 210, 190));
        categoryComboBox.setForeground(new Color(70, 50, 40));
        categoryComboBox.setPreferredSize(new Dimension(180, 35));
        categoryComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 180, 160), 2),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));

        categoryComboBox.addActionListener(e -> {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            loadBooks(selectedCategory);
        });

        JPanel categoryComboPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        categoryComboPanel.setOpaque(false);
        categoryComboPanel.add(categoryComboBox);

        filterPanel.add(searchPanel, BorderLayout.CENTER);
        filterPanel.add(categoryComboPanel, BorderLayout.EAST);

        JPanel categoriesBarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        categoriesBarPanel.setOpaque(false);
        categoriesBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

        for (String category : allCategories) {
            JButton categoryButton = createCategoryButton(category);
            categoriesBarPanel.add(categoryButton);
        }

        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setOpaque(false);
        topSection.add(titlePanel, BorderLayout.NORTH);
        topSection.add(filterPanel, BorderLayout.CENTER);
        topSection.add(categoriesBarPanel, BorderLayout.SOUTH);

        headerPanel.add(topSection, BorderLayout.NORTH);
        return headerPanel;
    }

    private JButton createCategoryButton(String category) {
        JButton button = new JButton(category);

        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setForeground(new Color(245, 235, 220));
        button.setBackground(new Color(100, 80, 70));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 180, 160), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setFocusPainted(false);

        button.addActionListener(e -> {
            categoryComboBox.setSelectedItem(category);
            loadBooks(category);
        });

        return button;
    }

    private void loadBooks(String category) {
        booksGridPanel.removeAll();

        booksGridPanel.setLayout(new GridLayout(0, 3, 20, 20));

        List<Book> booksToShow;

        if ("All books".equals(category)) {
            booksToShow = bookService.getAllBooks();
        } else {
            booksToShow = bookService.getBooksByCategory(category);
        }

        for (Book book : booksToShow) {
            booksGridPanel.add(new BookCardPanel(book, cartService));
        }

        if (booksToShow.isEmpty()) {
            JLabel noBooksLabel = new JLabel("No books found in this category");
            noBooksLabel.setForeground(new Color(210, 190, 170));
            noBooksLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
            noBooksLabel.setHorizontalAlignment(SwingConstants.CENTER);

            booksGridPanel.setLayout(new BorderLayout());
            booksGridPanel.add(noBooksLabel, BorderLayout.CENTER);
        }

        booksGridPanel.revalidate();
        booksGridPanel.repaint();
    }
}
