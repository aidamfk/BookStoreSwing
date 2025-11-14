package com.bookstoreswing.ui.controllers;

import com.bookstoreswing.service.BookService;
import com.bookstoreswing.service.CartService;
import com.bookstoreswing.ui.windows.HomeWindow;

/**
 * Controller for home view
 */
public class HomeController {
    private BookService bookService;
    private CartService cartService;
    private HomeWindow window;

    public HomeController(HomeWindow w){
        this.window = w;
        this.bookService = new BookService();
        this.cartService = new CartService();
    }

    public java.util.List<com.bookstoreswing.model.Book> getBooks(){
        return bookService.getAllBooks();
    }

    public CartService getCartService(){
        return cartService;
    }
}