//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.onlinegrocery.myapp.repository;

import com.onlinegrocery.myapp.model.Product;
import com.onlinegrocery.myapp.utils.ProductDetailsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
//    @Query(value = "select id, name, price, image, description, category, quantity, sold_quantity, ROUND((sold_quantity * 100.0 / (quantity + sold_quantity)), 2) as percentage_sold " +
//            "from product where sold_quantity > 0 " +
//            "order by percentage_sold desc " +
//            "limit 2", nativeQuery = true)
//    List<ProductDetailsDTO> getTopTwoProducts();

    @Query(value = "select * " +
            "from product where sold_quantity > 0 and quantity > 0 " +
            "order by sold_quantity desc " +
            "limit 2", nativeQuery = true)
    List<Product> getTopTwoProducts();
}
