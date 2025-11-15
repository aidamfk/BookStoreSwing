package com.bookstoreswing.ui.windows;

import java.util.ArrayList;
import java.util.List;

public class FavoriteManager {

    private static List<String> favoriteBooks = new ArrayList<>();

    public static void addBook(String bookName) {
        if (!favoriteBooks.contains(bookName)) {
            favoriteBooks.add(bookName);
        }
    }

    public static List<String> getFavoriteBooks() {
        return favoriteBooks;
    }
}
