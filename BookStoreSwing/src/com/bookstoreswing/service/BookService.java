package com.bookstoreswing.service;

import java.util.List;
import java.util.ArrayList;
import com.bookstoreswing.model.Book;
import com.bookstoreswing.data.BookData;

/**
 * Service to retrieve books
 */
public class BookService {
    
    public List<Book> getAllBooks(){
        // TEMPORAIRE: utilise seulement Fantasy pour tester
        return BookData.getFantasyBooks();
    }
    
    public List<Book> getBooksByCategory(String category) {
        // TEMPORAIRE: retourne toujours Fantasy
        return BookData.getFantasyBooks();
    }
    
    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        categories.add("All books");
        categories.add("Fantasy");
        categories.add("Guerre");
        return categories;
    }
}