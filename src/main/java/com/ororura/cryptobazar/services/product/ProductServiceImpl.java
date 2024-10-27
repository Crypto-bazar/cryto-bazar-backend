package com.ororura.cryptobazar.services.product;

import com.ororura.cryptobazar.dtos.ProductDTO;
import com.ororura.cryptobazar.entities.product.ProductEntity;
import com.ororura.cryptobazar.entities.user.UserEntity;
import com.ororura.cryptobazar.repositories.ProductRepo;
import com.ororura.cryptobazar.services.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final UserService userService;

    public ProductServiceImpl(ProductRepo productRepo, UserService userService) {
        this.productRepo = productRepo;
        this.userService = userService;
    }

    @Override
    public ProductEntity createProduct(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDTO.getName());
        productEntity.setDescription(productDTO.getDescription());

        UserEntity owner = this.userService.findUserById(productDTO.getOwnerId());
        productEntity.setOwnerId(owner);

        return productRepo.save(productEntity);
    }

    @Override
    public ProductEntity getProductById(Integer id) {
        return productRepo.findFirstById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public void deleteProductById(Integer id) {
        this.productRepo.deleteById(id);
    }
}
