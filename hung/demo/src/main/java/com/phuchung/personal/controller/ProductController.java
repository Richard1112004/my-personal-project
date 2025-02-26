package com.phuchung.personal.controller;

import com.phuchung.personal.model.Product;
import com.phuchung.personal.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        Product product = productService.getProductsById(id);
        if (product!=null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestPart Product product,
                                              @RequestPart MultipartFile imageFile){
        try{
            Product newProduct = productService.addProduct(product, imageFile);
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);

        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImageByProductID(@PathVariable int id){
        Product product = productService.getProductsById(id);
        byte [] imageData = product.getImageData();
        return ResponseEntity.ok().body(imageData);
    }
    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product,
                                                 @RequestPart MultipartFile imageFile) throws IOException {
        Product product1 = productService.updateProduct(id, product, imageFile);
        if (product1!=null)
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to update" ,HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> DeleteProduct(@PathVariable int id){
        Product product1 = productService.getProductsById(id);
        if (product1!=null) {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Failed to delete" ,HttpStatus.NOT_FOUND);
    }
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword){
        List<Product> products = productService.searchProducts(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}

