package com.onlinegrocery.myapp.utils;


import java.math.BigDecimal;

public class ProductDetailsDTO {

    private int id;
    private String name;
    private double price;

    private String image;
    private String description;
    private String category;
    private int quantity;


    private int soldQuantity;
    private BigDecimal percentageSold;

    public ProductDetailsDTO(){}

    public ProductDetailsDTO(int id, String name, double price, String image, String description, String category, int quantity, int soldQuantity, BigDecimal percentageSold) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.soldQuantity = soldQuantity;
        this.percentageSold = percentageSold;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public BigDecimal getPercentageSold() {
        return percentageSold;
    }

    public void setPercentageSold(BigDecimal percentageSold) {
        this.percentageSold = percentageSold;
    }
}
