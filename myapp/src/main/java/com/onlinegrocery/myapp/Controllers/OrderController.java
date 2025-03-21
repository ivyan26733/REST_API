package com.onlinegrocery.myapp.Controllers;

import com.onlinegrocery.myapp.model.Orders;
import com.onlinegrocery.myapp.model.User;
import com.onlinegrocery.myapp.service.OrderService;
import com.onlinegrocery.myapp.service.UserService;
import com.onlinegrocery.myapp.utils.ApiResponse;
import com.onlinegrocery.myapp.utils.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/protected/orders")
@CrossOrigin
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<Orders>>> getOrdersByUser(@PathVariable int userId){
        List<Orders> orders = orderService.findOrdersByUserId(userId);
        ApiResponse<List<Orders>> res = new ApiResponse<>("Orders fetched successfully!", orders, true);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<Orders>> createOrder(@RequestBody OrderDTO dto){
        Orders order = orderService.createNewOrder(dto.getUserId(), dto.getProducts());
        ApiResponse<Orders> res = new ApiResponse<>("Order created successfully", order, true);
        return ResponseEntity.ok(res);

    }
}
