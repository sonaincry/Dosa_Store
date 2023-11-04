package com.example.dosa_store.model;


import org.threeten.bp.LocalDateTime;

public class Product {
    private int id;
    private String name;
    private float price;
    private int quantity;
    private String description;
    private String imgUrl;
    //  private LocalDateTime createdDate;
//
    public Product(int id, String name, float price, int quantity, String description, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.imgUrl = imgUrl;
        //  this.createdDate = createdDate;
    }

    public Product(String name, float price, int quantity, String description, String imgUrl) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.imgUrl = imgUrl;
        //  this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
//
//    public LocalDateTime getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(LocalDateTime createdDate) {
//        this.createdDate = createdDate;
//    }
}
