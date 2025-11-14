package com.bookstoreswing.model;

/**
 * Item in cart with quantity
 */
public class CartItem {
    private Book book;
    private int quantity;

    public CartItem(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int qty) {
        this.quantity = qty;
    }

    // Increase quantity
    public void increaseQuantity() {
        this.quantity++;
    }

    // Decrease quantity
    public void decreaseQuantity() {
        if (this.quantity > 1) {
            this.quantity--;
        }
    }

    // === ADD THIS METHOD ===
    public double getTotal() {
        return book.getPrice() * quantity;
    }
}


    
