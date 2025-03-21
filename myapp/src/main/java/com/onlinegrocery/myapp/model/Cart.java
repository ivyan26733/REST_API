//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.onlinegrocery.myapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int id;
    private int userId;
    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = {@JoinColumn(
                    name = "cart_id"
            )},
            inverseJoinColumns = {@JoinColumn(
                    name = "product_id"
            )}
    )
    private List<Product> items;

    public Cart(){

    }

    public Cart(int id, int userId, List<Product> items) {
        this.id = id;
        this.userId = userId;
        this.items = items;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Product> getItems() {
        return this.items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }
}
