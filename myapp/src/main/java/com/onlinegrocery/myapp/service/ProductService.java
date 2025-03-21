//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.onlinegrocery.myapp.service;

import com.onlinegrocery.myapp.model.Product;
import com.onlinegrocery.myapp.repository.ProductRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.onlinegrocery.myapp.utils.ProductDetailsDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public ProductService() {
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return (Product)this.productRepository.save(product);
    }

    public Product findProductById(int id) {
        return (Product)this.productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found for product id: " + id));
    }

    public Product updateProduct(Product product) {
        return (Product)this.productRepository.save(product);
    }

    public void deleteProductById(int id) {
        this.productRepository.deleteById(id);
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public List<Product> getTopTwoProducts() {
//        List<ProductDetailsDTO> topTwoProducts = productRepository.getTopTwoProducts();
        List<Product> topTwoProducts = productRepository.getTopTwoProducts();
//        List<Product> topProducts = topTwoProducts.stream().map(dto -> new Product(dto.getId(), dto.getName(), dto.getPrice(), dto.getImage(), dto.getDescription(), dto.getCategory(), dto.getQuantity(), dto.getSoldQuantity())).collect(Collectors.toList());
        return topTwoProducts;
    }
}
