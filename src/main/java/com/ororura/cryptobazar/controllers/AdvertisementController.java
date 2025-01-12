package com.ororura.cryptobazar.controllers;

import com.ororura.cryptobazar.dtos.ProductDTO;
import com.ororura.cryptobazar.entities.Advertisement;
import com.ororura.cryptobazar.services.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/advertisements")
public class AdvertisementController {
    private final ProductService productService;
    private static final String UPLOAD_DIR = "src/main/resources/uploads/";

    public AdvertisementController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productEntities = productService.getAllProducts();
        return new ResponseEntity<>(productEntities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Advertisement> getProductById(@PathVariable Integer id) {
        Advertisement product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Advertisement> addProduct(@ModelAttribute ProductDTO product, @RequestParam MultipartFile file) {
        Advertisement createdProduct = productService.createProduct(product, file);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@RequestParam("id") Integer id) {
        this.productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
