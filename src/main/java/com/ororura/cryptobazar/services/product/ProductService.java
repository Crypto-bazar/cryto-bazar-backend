package com.ororura.cryptobazar.services.product;

import com.ororura.cryptobazar.dtos.ProductDTO;
import com.ororura.cryptobazar.entities.Advertisement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    @Transactional
    Advertisement createProduct(ProductDTO productDTO, MultipartFile file);

    @Transactional
    Advertisement getProductById(Integer id);

    @Transactional
    List<ProductDTO> getAllProducts();

    @Transactional
    void deleteProductById(Integer id);

    void saveImage(MultipartFile file) throws IOException;
}
