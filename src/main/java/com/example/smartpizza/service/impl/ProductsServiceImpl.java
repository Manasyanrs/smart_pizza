package com.example.smartpizza.service.impl;

import com.example.smartpizza.entity.productEntity.Product;
import com.example.smartpizza.entity.productEntity.ProductType;
import com.example.smartpizza.repository.ProductRepository;
import com.example.smartpizza.service.LoadAndUploadImgService;
import com.example.smartpizza.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {
    @Value("${products.images.path}")
    private String imageUploadPath;
    private final LoadAndUploadImgService loadAndUploadImgService;
    private final ProductRepository productRepository;


    @Override
    public List<Product> takeRandomProducts() {
        return productRepository.takeRandomProduct();
    }

    @Override
    public Page<Product> createPageable(Optional<Integer> size, Optional<Integer> page, ProductType productType) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(6);

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        return productRepository.findProductsByProductType(productType, pageable);
    }

    @Override
    public Product save(Product product, MultipartFile multipartFile) throws IOException {
        String productImageName = loadAndUploadImgService.uploadImg(imageUploadPath, multipartFile);
        product.setProductImg(productImageName);
        return productRepository.save(product);
    }


    @Override
    public void DeleteById(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> searchProductByProductType(String productType) {
        return productRepository.findProductsByProductType(ProductType.valueOf(productType));
    }

    @Override
    public Product searchProductById(int id) {
        return productRepository.findProductsById(id);
    }

}
