package com.ororura.cryptobazar.services.product;

import com.ororura.cryptobazar.dtos.ProductDTO;
import com.ororura.cryptobazar.entities.Advertisement;
import com.ororura.cryptobazar.repositories.AdvertisementRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final AdvertisementRepo advertisementRepo;

    public ProductServiceImpl(AdvertisementRepo advertisementRepo) {
        this.advertisementRepo = advertisementRepo;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Advertisement> products = advertisementRepo.findAll();
        return products.stream().map(productEntity -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setAmount(productEntity.getAmount());
            productDTO.setPhoto(productEntity.getPhoto());
            productDTO.setName(productEntity.getName());
            productDTO.setDescription(productEntity.getDescription());
            productDTO.setOwner(productEntity.getOwner().getName());
            return productDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public Advertisement createProduct(ProductDTO productDTO, MultipartFile file) {
        Advertisement productEntity = new Advertisement();
        productEntity.setName(productDTO.getName());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setAmount(productDTO.getAmount());

        try {
            saveImage(file);

        } catch (IOException e) {
            throw new IllegalStateException("Could not save image", e);
        }
        return advertisementRepo.save(productEntity);
    }

    @Override
    public Advertisement getProductById(Integer id) {
        return advertisementRepo.findFirstById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public void deleteProductById(Integer id) {
        this.advertisementRepo.deleteById(id);
    }

    @Override
    public void saveImage(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path imagePath = Paths.get("uploads/", filename);
        Files.createDirectories(imagePath.getParent());
        Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
    }
}
