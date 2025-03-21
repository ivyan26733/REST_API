package com.onlinegrocery.myapp.service;

import com.onlinegrocery.myapp.model.Orders;
import com.onlinegrocery.myapp.model.Product;
import com.onlinegrocery.myapp.model.User;
import com.onlinegrocery.myapp.repository.OrderRepository;
import com.onlinegrocery.myapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    public List<Orders> findOrdersByUserId(int userId) {
        List<Orders> orders = orderRepository.findByUserId(userId);
        return orders;

    }

    public Orders createNewOrder(int userId, List<Product> products) {

        User user = userRepository.findById(userId).orElse(null);

        Orders order = new Orders();

        order.setUser(user);
        order.setItems(products);

        double totalAmount = 0;
        for (Product p: products){
            totalAmount += (p.getPrice() * p.getQuantity());
        }

        order.setTotalAmount(totalAmount);

        order.setOrderDate(new Date());
        return orderRepository.save(order);
    }
}
