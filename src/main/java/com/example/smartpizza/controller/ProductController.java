package com.example.smartpizza.controller;

import com.example.smartpizza.entity.productEntity.Product;

import com.example.smartpizza.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductsService productsService;


    @GetMapping("/get_product_by_type")
    public String getProductByType(@RequestParam String productType,ModelMap modelMap){
        modelMap.addAttribute("productType",productType);
        modelMap.addAttribute("products",productsService.searchProductByProductType(productType));
        return "picks_today";
        }

        @GetMapping("/single_page/{id}")
    public String productSinglePage(@PathVariable int id,ModelMap modelMap){
        Product product = productsService.searchProductById(id);
        modelMap.addAttribute("product",product);
        modelMap.addAttribute("productList",productsService.randomProduct());
        return "product_details";
        }





}
