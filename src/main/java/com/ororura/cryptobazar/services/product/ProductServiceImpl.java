package com.ororura.cryptobazar.services.product;

import com.ororura.cryptobazar.dtos.ProductDTO;
import com.ororura.cryptobazar.entities.product.ProductEntity;
import com.ororura.cryptobazar.entities.user.UserEntity;
import com.ororura.cryptobazar.repositories.ProductRepo;
import com.ororura.cryptobazar.services.user.UserServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final UserServiceImpl userServiceImpl;

    public ProductServiceImpl(ProductRepo productRepo, UserServiceImpl userServiceImpl) {
        this.productRepo = productRepo;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public ProductEntity createProduct(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDTO.getName());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setAmount(productDTO.getAmount());

        UserEntity owner = this.userServiceImpl.findUserById(productDTO.getOwnerId());
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
