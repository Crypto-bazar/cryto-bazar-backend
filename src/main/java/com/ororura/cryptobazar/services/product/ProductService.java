package com.ororura.cryptobazar.services.product;

import com.ororura.cryptobazar.dtos.ProductDTO;
import com.ororura.cryptobazar.entities.product.ProductEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    @Transactional
    ProductEntity createProduct(ProductDTO productDTO, MultipartFile file);

    @Transactional
    ProductEntity getProductById(Integer id);

    @Transactional
    List<ProductDTO> getAllProducts();

    @Transactional
    void deleteProductById(Integer id);

    String saveImage(MultipartFile file) throws IOException;
}
