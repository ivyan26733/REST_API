//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.onlinegrocery.myapp.Controllers;

import com.onlinegrocery.myapp.model.Cart;
import com.onlinegrocery.myapp.model.Product;
import com.onlinegrocery.myapp.service.CartService;
import com.onlinegrocery.myapp.utils.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping({"/protected/carts/customer"})
@CrossOrigin
public class CartController {
    @Autowired
    CartService cartService;

    public CartController() {}

    @GetMapping({""})
    public ApiResponse<Cart> getCartByUserId(@RequestParam(value = "userId") int userId) {
        Cart cart = this.cartService.getCartByUserId(userId);
        ApiResponse<Cart> res = new ApiResponse("Cart details fetched successfully!", cart, true);
        return res;
    }

    @PostMapping({"/"})
    public ApiResponse<Cart> createCart(@RequestBody Cart cart) {
        Cart addedCart = this.cartService.addCart(cart);
        ApiResponse<Cart> res = new ApiResponse("Cart created successfully!", addedCart, true);
        return res;
    }

    @PutMapping({"/{cartId}"})
    public ApiResponse<Cart> updateCart(@RequestBody Cart cart, @PathVariable int cartId) {
        Cart cartToUpdate = cartService.findById(cartId);
        Cart updatedCart = null;
        if(cartToUpdate != null){
            updatedCart = this.cartService.updatedCart(cartToUpdate, cart);
        }

        ApiResponse<Cart> res = new ApiResponse("Cart updated successfully!", updatedCart, true);
        return res;
    }

    @DeleteMapping({"/{cartId}"})
    public ApiResponse<String> deleteCart(@PathVariable int cartId) {
        this.cartService.deleteProductById(cartId);
        ApiResponse<String> res = new ApiResponse("Cart deleted successfully!", null, true);
        return res;
    }

    @Transactional
    @PutMapping("/process")
    public ResponseEntity<ApiResponse<String>> processCart(@RequestBody List<Product> products) {

        boolean result = cartService.processCart(products);
        return ResponseEntity.ok(new ApiResponse<String>("Cart processed successfully!", "SUCCESS", true));

    }





}
