//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.onlinegrocery.myapp.service;

import com.onlinegrocery.myapp.model.Cart;
import com.onlinegrocery.myapp.model.Product;
import com.onlinegrocery.myapp.repository.CartRepository;
import com.onlinegrocery.myapp.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    public CartService() {
    }

    public Cart addCart(Cart cart) {
        return (Cart)this.cartRepository.save(cart);
    }

    public Cart updatedCart(Cart cartToUpdate, Cart cart) {
        cartToUpdate.setItems(cart.getItems());
        cartRepository.save(cartToUpdate);
        return cartToUpdate;
    }

    public void deleteProductById(int cartId) {
        this.cartRepository.deleteById(cartId);
    }

    public Cart getCartByUserId(int userId) {
        return this.cartRepository.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException("Cart not found for user Id: "+userId));
    }

    public Cart findById(int cartId) {
        Cart c = cartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found for id: "+ cartId));
        return c;
    }

    public boolean processCart(List<Product> products) {
        for (Product p : products){
            int prodId = p.getId();
            Product prodDB = productRepository.findById(prodId).orElseThrow(() -> new EntityNotFoundException("Cart processing failed! Product Id not found: " + prodId));

            prodDB.setQuantity(prodDB.getQuantity() - p.getQuantity());
            prodDB.setSoldQuantity(prodDB.getSoldQuantity() + p.getQuantity());
            productRepository.save(prodDB);
        }
        return true;
    }
}
