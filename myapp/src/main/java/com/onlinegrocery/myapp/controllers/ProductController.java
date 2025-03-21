//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.onlinegrocery.myapp.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinegrocery.myapp.model.Product;
import com.onlinegrocery.myapp.service.ProductService;
import com.onlinegrocery.myapp.utils.ApiResponse;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/protected/products")
public class ProductController {
    @Autowired
    ProductService productService;

    public ProductController() {
    }

    @GetMapping("/")
    public ApiResponse<List<Product>> getAllProducts() {
        List<Product> products = this.productService.getAllProducts();
        ApiResponse<List<Product>> res = new ApiResponse("Products details fetched successfully!", products, true);
        return res;
    }

    @PostMapping("/admin/add")
    public ApiResponse<Product> addProduct(@RequestPart("product") String productJson ,@RequestPart("image") MultipartFile imageFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(productJson , Product.class);

        Product addProduct = this.productService.addProduct(product , imageFile);
        ApiResponse<Product> res = new ApiResponse("Product added successfully!", addProduct, true);
        return res;
    }

    @GetMapping("/admin/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        Product product = productService.findProductById(productId);
        byte[] imageFile = product.getImageData();

        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
    }

    @GetMapping({"/{id}"})
    public ApiResponse<Product> getProductById(@PathVariable int id) {
        Product product = this.productService.findProductById(id);
        ApiResponse<Product> res = new ApiResponse("Product details fetched successfully!", product, true);
        return res;
    }

    @DeleteMapping({"/admin/{id}"})
    public ApiResponse<String> deleteProductById(@PathVariable int id) {
        this.productService.deleteProductById(id);
        ApiResponse<String> res = new ApiResponse("Product deleted successfully", null, true);
        return res;
    }

    @PutMapping({"/admin/update"})
    public ApiResponse<Product> updateProduct(@RequestBody Product product) {
        Product updatedProduct = this.productService.updateProduct(product);
        ApiResponse<Product> res = new ApiResponse("Product details updated successfully!", updatedProduct, true);
        return res;
    }

    @GetMapping("/getTopProducts")
    public ResponseEntity<ApiResponse<List<Product>>> getTopRatedProducts(){
        try{
            List<Product> topRatedProducts = productService.getTopTwoProducts();
            ApiResponse<List<Product>> res = new ApiResponse("Top rated products fetched successfully!", topRatedProducts, true);
            return ResponseEntity.ok(res);
        }catch(Exception e){
            e.printStackTrace();
            ApiResponse<List<Product>> res = new ApiResponse("Error!", null, false);
            return ResponseEntity.ok(res);
        }
    }

}
