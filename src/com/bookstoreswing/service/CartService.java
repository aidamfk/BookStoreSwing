package com.bookstoreswing.service;

import java.util.*;
import com.bookstoreswing.model.*;

/**
 * Manages the shopping cart
 */
public class CartService {
    private final List<CartItem> items = new ArrayList<>();

    public void addBook(Book b) {
        for (CartItem it : items) {
            if (it.getBook().getId().equals(b.getId())) {
                it.setQuantity(it.getQuantity() + 1);
                return;
            }
        }
        items.add(new CartItem(b, 1));
    }

    public void removeAt(int index) {
        if (index >= 0 && index < items.size()) items.remove(index);
    }

    public List<CartItem> getItems() { 
        return items; 
    }

    public double getTotal() {
        double sum = 0;
        for (CartItem it : items) sum += it.getTotal();
        return sum;
    }

    public void clear() { 
        items.clear(); 
    }

    // ✅ FINAL: ALWAYS modify the SAME CartItem that CartPage uses
    public void increaseQuantity(int index) {
        if (index >= 0 && index < items.size()) {
            CartItem it = items.get(index);
            it.setQuantity(it.getQuantity() + 1);
        }
    }

    public void decreaseQuantity(int index) {
        if (index >= 0 && index < items.size()) {
            CartItem it = items.get(index);
            if (it.getQuantity() > 1) {
                it.setQuantity(it.getQuantity() - 1);
            }
        }
    }
}
