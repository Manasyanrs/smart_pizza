package com.example.smartpizza.service;

import com.example.smartpizza.entity.productEntity.Product;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


public interface ProductsService {

    Product save(Product product, MultipartFile multipartFile) throws Exception;

    void DeleteById(int id);

    List<Product> searchProductByProductType(String productType);


    Product searchProductById(int id);

    List<Product> takeRandomProducts();
    List<Product> randomProduct();


}
