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
        return BookData.getAllBooksWithCategories();
    }
    
    public List<Book> getBooksByCategory(String category) {
        switch (category) {
            case "Fantasy":
                return BookData.getFantasyBooks();
            case "Guerre":
                return BookData.getGuerreBooks();
            case "Romance":
            case "Historical":
            case "All books":
            default:
                return BookData.getAllBooksWithCategories();
        }
    }
    
    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        categories.add("All books");
        categories.add("Romance");
        categories.add("Historical");
        categories.add("Fantasy");
        categories.add("Guerre");
        return categories;
    }
}