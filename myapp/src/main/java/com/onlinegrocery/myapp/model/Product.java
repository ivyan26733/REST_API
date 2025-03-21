//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.onlinegrocery.myapp.model;

import jakarta.persistence.*;


import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int id;
    private String name;
    private double price;
    private String description;
    private String category;
    private int quantity;

    private String imageName;
    private String imageType;

//    We can store the image in cloud storage and get the link (best way)
    @Lob
    private byte[] imageData;
    

    @Column(name = "sold_quantity", columnDefinition = "integer default 0")
    private int soldQuantity;

    @ManyToMany(
            mappedBy = "items"
    )
    private List<Cart> carts;

    @ManyToMany(
            mappedBy = "items"
    )
    private List<Orders> orders;

    public Product() {
    }

    public Product(int product_id, String name, double price, String description, String category, int quantity, int soldQuantity,String imageName, String imageType, byte[] imageData) {
        this.id = product_id;
        this.name = name;
        this.price = price;

        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.soldQuantity = soldQuantity;
        this.imageName = imageName;
        this.imageType = imageType;
        this.imageData = imageData;
    }


    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int product_id) {
        this.id = product_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return this.quantity;
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
}
