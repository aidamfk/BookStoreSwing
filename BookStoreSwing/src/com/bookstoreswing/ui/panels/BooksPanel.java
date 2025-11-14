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

    private BookService bookService;
    private CartService cartService;
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
        
        // Header avec titre et filtres
        add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Grid des livres
        booksGridPanel = new JPanel();
        booksGridPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        booksGridPanel.setOpaque(false);
        booksGridPanel.setBackground(new Color(0, 0, 0, 0));
        
        JScrollPane scrollPane = new JScrollPane(booksGridPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollPane, BorderLayout.CENTER);
        
        // Charger tous les livres au démarrage
        loadBooks("All books");
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Titre
        JLabel titleLabel = new JLabel("Our Collection");
        titleLabel.setForeground(new Color(245, 235, 220));
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        
        JLabel subtitleLabel = new JLabel("Browse through our extensive collection of rare and vintage literature");
        subtitleLabel.setForeground(new Color(210, 190, 170));
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Filtres - VERSION AMÉLIORÉE
        JPanel filterPanel = new JPanel(new BorderLayout(20, 0)); // Espacement de 20px
        filterPanel.setOpaque(false);

        // Barre de recherche large (prend tout l'espace disponible)
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);
        
        JTextField searchField = new JTextField();
        searchField.setText("Search by title or authors...");
        searchField.setForeground(new Color(100, 80, 70));
        searchField.setBackground(new Color(220, 200, 180)); // Marron clair
        searchField.setCaretColor(new Color(100, 80, 70));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 180, 160), 2), // Bordure beige
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        // Bouton de recherche avec icône
        JButton searchButton = new JButton("🔍");
        searchButton.setPreferredSize(new Dimension(50, 35));
        searchButton.setBackground(new Color(180, 150, 130)); // Marron moyen
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 160), 1));
        searchButton.setFocusPainted(false);
        searchButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        
        // Catégories avec icône
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        categoryPanel.setOpaque(false);
        
        // ComboBox des catégories avec style marron
        categoryComboBox = new JComboBox<>();
        for (String category : bookService.getCategories()) {
            categoryComboBox.addItem(category);
        }
        
        // Style de la ComboBox
        categoryComboBox.setBackground(new Color(220, 200, 180)); // Marron clair
        categoryComboBox.setForeground(new Color(100, 80, 70));
        categoryComboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 180, 160), 2), // Bordure beige
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        categoryComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        categoryComboBox.setPreferredSize(new Dimension(180, 35));
        
        // Ajouter l'icône 📚 devant le texte
        categoryComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setText("📚 " + value.toString()); // Icône + texte
                label.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
                return label;
            }
        });
        
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryComboBox.getSelectedItem();
                loadBooks(selectedCategory);
            }
        });

        categoryPanel.add(categoryComboBox);
        
        // Disposition: recherche prend tout l'espace, catégories à droite
        filterPanel.add(searchPanel, BorderLayout.CENTER); // CENTER pour prendre tout l'espace
        filterPanel.add(categoryPanel, BorderLayout.EAST);

        headerPanel.add(titlePanel, BorderLayout.NORTH);
        headerPanel.add(filterPanel, BorderLayout.SOUTH);

        return headerPanel;
    }

    private void loadBooks(String category) {
        // Vider le panel actuel
        booksGridPanel.removeAll();
        
        // Changer le layout pour GridLayout pour afficher plusieurs livres
        booksGridPanel.setLayout(new GridLayout(0, 3, 20, 20)); // 3 colonnes, espacement 20px
        
        // Récupérer les livres selon la catégorie
        List<Book> booksToShow;
        if ("All books".equals(category)) {
            booksToShow = bookService.getAllBooks();
        } else {
            booksToShow = bookService.getBooksByCategory(category);
        }
        
        // Ajouter les BookCardPanel pour chaque livre
        for (Book book : booksToShow) {
            BookCardPanel bookCard = new BookCardPanel(book, cartService);
            booksGridPanel.add(bookCard);
        }
        
        // Rafraîchir l'affichage
        booksGridPanel.revalidate();
        booksGridPanel.repaint();
        
        // Si aucun livre trouvé
        if (booksToShow.isEmpty()) {
            JLabel noBooksLabel = new JLabel("No books found in this category");
            noBooksLabel.setForeground(new Color(210, 190, 170));
            noBooksLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
            noBooksLabel.setHorizontalAlignment(SwingConstants.CENTER);
            booksGridPanel.setLayout(new BorderLayout());
            booksGridPanel.add(noBooksLabel, BorderLayout.CENTER);
        }
    }
}