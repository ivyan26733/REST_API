package com.onlinegrocery.myapp.repository;

import com.onlinegrocery.myapp.model.Orders;
import com.onlinegrocery.myapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByUserId(int userId);
}
