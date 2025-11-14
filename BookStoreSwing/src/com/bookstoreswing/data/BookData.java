package com.bookstoreswing.data;

import java.util.*;
import com.bookstoreswing.model.Book;

/**
 * Simple in-memory data provider for books
 */
public class BookData {

    public static List<Book> getSampleBooks() {
        List<Book> list = new ArrayList<>();
        list.add(new Book("B001","Java: From Zero to Hero","Ali Ahmed",3500,"books/Fantasy1.jpg", "Programming"));
        list.add(new Book("B002","Clean Code","Robert C. Martin",4200,"books/Fantasy2.jpg", "Programming"));
        list.add(new Book("B003","Effective Java","Joshua Bloch",3900,"books/Fantasy3.jpg", "Programming"));
        list.add(new Book("B004","Algorithms in Java","Thomas H. Cormen",4500,"books/Fantasy4.jpg", "Programming"));
        list.add(new Book("B005","Design Patterns","Erich Gamma",4100,"books/Fantasy5.jpg", "Programming"));
        list.add(new Book("B006","The Pragmatic Programmer","Andrew Hunt",3700,"books/Fantasy1.jpg", "Programming"));
        return list;
    }

    public static List<Book> getFantasyBooks() {
        List<Book> list = new ArrayList<>();

        list.add(new Book("F001",
                "Panorama illustré de la fantasy et du merveilleux",
                "André-François Ruaud",
                1800,
                "books/Fantasy1.jpg",
                "Fantasy"
        ));

        list.add(new Book("F002",
                "La Légende Final Fantasy VII", 
                "Nicolas Courcéer",
                1480,
                "books/Fantasy2.jpg",
                "Fantasy"
        ));

        list.add(new Book("F003",
                "Le langage de la nuit - Essais sur la science-fiction et la fantasy",
                "Ursula K. Le Guin", 
                1480,
                "books/Fantasy3.jpg",
                "Fantasy"
        ));

        list.add(new Book("F004",
                "Dictionnaire de la fantasy",
                "Anne Besson",
                1730,
                "books/Fantasy4.jpg",
                "Fantasy"
        ));

        list.add(new Book("F005",
                "L'Impératrice remariée, Tome 1",
                "Alphatart",  // CORRECTION ICI
                1420,
                "books/Fantasy5.jpg",
                "Fantasy"
       
        ));

        return list;
    }
      

    public static List<Book> getGuerreBooks() {
        List<Book> list = new ArrayList<>();

        list.add(new Book("G001",
                "Prince captif",
                "C.S. Pach",
                1739,
                "books/Fantasy1.jpg",
                "Guerre"
        ));

        list.add(new Book("G002",
                "Dent d'ours, Tome 2 - Hanna",
                "Yann & Alain Henriet",
                1650,
                "books/Fantasy2.jpg",
                "Guerre"
        ));

        list.add(new Book("G003",
                "Guerre",
                "Louis Ferdinand Céline",
                2700,
                "books/Fantasy3.jpg",
                "Guerre"
        ));

        list.add(new Book("G004",
                "La Guerre du pavot, Tome 1",
                "Rebecca Kuang",
                1550,
                "books/Fantasy4.jpg",
                "Guerre"
        ));

        return list;
    }

    // AJOUTE cette méthode pour avoir tous les livres ensemble
    public static List<Book> getAllBooksWithCategories() {
        List<Book> allBooks = new ArrayList<>();
        allBooks.addAll(getSampleBooks());
        allBooks.addAll(getFantasyBooks());
        allBooks.addAll(getGuerreBooks());
        return allBooks;
    }
}