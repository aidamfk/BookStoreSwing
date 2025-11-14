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

        // Filtres - VERSION FINALE
        JPanel filterPanel = new JPanel(new BorderLayout(20, 0));
        filterPanel.setOpaque(false);

        // Barre de recherche large
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);
        
        JTextField searchField = new JTextField();
        searchField.setText("Search by title or authors...");
        searchField.setForeground(new Color(80, 60, 50)); // Texte plus foncé pour meilleure visibilité
        searchField.setBackground(new Color(220, 200, 180));
        searchField.setCaretColor(new Color(80, 60, 50));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 180, 160), 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        // Bouton de recherche
        JButton searchButton = new JButton("🔍");
        searchButton.setPreferredSize(new Dimension(50, 35));
        searchButton.setBackground(new Color(180, 150, 130));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 160), 1));
        searchButton.setFocusPainted(false);
        searchButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        
        // Catégories avec meilleure visibilité
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        categoryPanel.setOpaque(false);
        
        // ComboBox des catégories avec texte plus visible
        categoryComboBox = new JComboBox<>();
        
        // AJOUT: Toutes les catégories comme sur ta photo
        String[] allCategories = {"All books", "Romance", "Historical", "Fantasy", "Guerre"};
        for (String category : allCategories) {
            categoryComboBox.addItem(category);
        }
        
        // Style amélioré pour meilleure visibilité
        categoryComboBox.setBackground(new Color(220, 200, 180));
        categoryComboBox.setForeground(new Color(80, 60, 50)); // Texte plus foncé
        categoryComboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 180, 160), 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        categoryComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        categoryComboBox.setPreferredSize(new Dimension(180, 35));
        
        // Renderer avec meilleure visibilité
        categoryComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                
                // Icônes différentes selon la catégorie
                String icon = "📚"; // Icône par défaut
                if ("Romance".equals(value)) icon = "❤️";
                else if ("Historical".equals(value)) icon = "🏛️";
                else if ("Fantasy".equals(value)) icon = "🧙";
                else if ("Guerre".equals(value)) icon = "⚔️";
                
                label.setText(icon + " " + value.toString());
                label.setForeground(new Color(80, 60, 50)); // Texte foncé pour bonne visibilité
                label.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
                label.setFont(new Font("SansSerif", Font.PLAIN, 13));
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
        
        // Disposition
        filterPanel.add(searchPanel, BorderLayout.CENTER);
        filterPanel.add(categoryPanel, BorderLayout.EAST);

        headerPanel.add(titlePanel, BorderLayout.NORTH);
        headerPanel.add(filterPanel, BorderLayout.SOUTH);

        return headerPanel;
    }

    private void loadBooks(String category) {
        // Vider le panel actuel
        booksGridPanel.removeAll();
        
        // Changer le layout pour GridLayout pour afficher plusieurs livres
        booksGridPanel.setLayout(new GridLayout(0, 3, 20, 20));
        
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