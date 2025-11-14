package com.bookstoreswing.model;

/**
 * Model class representing a Book
 */
public class Book {
    private String id;
    private String title;
    private String author;
    private double price;
    private String imagePath;
    private String category; // AJOUT: pour Fantasy/Guerre/etc.

    // MODIFIE le constructeur
    public Book(String id, String title, String author, double price, String imagePath, String category) {
        this.id = id; 
        this.title = title; 
        this.author = author;
        this.price = price; 
        this.imagePath = imagePath;
        this.category = category; // AJOUT
    }

    public String getId(){ return id; }
    public String getTitle(){ return title; }
    public String getAuthor(){ return author; }
    public double getPrice(){ return price; }
    public String getImagePath(){ return imagePath; }
    
    // AJOUTE ce getter pour la catégorie
    public String getCategory(){ return category; }
}