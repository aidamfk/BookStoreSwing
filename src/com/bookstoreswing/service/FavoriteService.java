package com.bookstoreswing.service;

import com.bookstoreswing.model.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Simple in-memory favorites service.
 * Keeps insertion order and prevents duplicates by id.
 */
public class FavoriteService {
    private final List<Book> favs = new ArrayList<>();

    public synchronized void add(Book b) {
        if (b == null) return;
        for (Book ex : favs) {
            if (ex.getId().equals(b.getId())) return;
        }
        favs.add(b);
    }

    public synchronized void remove(Book b) {
        if (b == null) return;
        favs.removeIf(x -> x.getId().equals(b.getId()));
    }

    public synchronized boolean has(Book b) {
        if (b == null) return false;
        for (Book ex : favs) if (ex.getId().equals(b.getId())) return true;
        return false;
    }

    public synchronized List<Book> getAll() {
        return Collections.unmodifiableList(new ArrayList<>(favs));
    }

    public synchronized int size() {
        return favs.size();
    }

    public synchronized void clear() {
        favs.clear();
    }
}
