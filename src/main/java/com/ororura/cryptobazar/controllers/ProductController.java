package com.ororura.cryptobazar.controllers;

import com.ororura.cryptobazar.dtos.ProductDTO;
import com.ororura.cryptobazar.entities.product.ProductEntity;
import com.ororura.cryptobazar.services.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ProductEntity> getProductById(@RequestParam("id") Integer id) {
        ProductEntity productEntity = productService.getProductById(id);
        return new ResponseEntity<>(productEntity, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductEntity> addProduct(@RequestBody ProductDTO product) {
        ProductEntity createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@RequestParam("id") Integer id) {
        this.productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
