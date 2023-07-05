package com.example.smartpizza.controller;

import com.example.smartpizza.entity.productEntity.Product;
import com.example.smartpizza.entity.productEntity.ProductType;
import com.example.smartpizza.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    @Value("${products.images.path}")
    private String imageUploadPath;

    private final ProductsService productsService;

    @GetMapping("/addProduct")
    public String getProductPage(ModelMap modelMap) {
        ProductType [] productTypes = ProductType.values();
        modelMap.addAttribute("productTypes",productTypes);
        return "/manager/promos";
    }

    @PostMapping("/addProduct")
    public String saveProductInRepo(@ModelAttribute Product product,
                                    @RequestParam("image") MultipartFile multipartFile) throws Exception {


        productsService.save(product, multipartFile);
        return "redirect:/manager/addProduct";
    }

    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("id") int id) {
        productsService.DeleteById(id);
        return "redirect:/manager/addProduct";
    }

    @GetMapping("/takeProductByType")
    public String takeByProductType(RedirectAttributes modelMap, @RequestParam("productType") String productType) {
        modelMap.addFlashAttribute("takeProductByType", productsService.searchProductByProductType(productType));
        return "redirect:/manager/addProduct";
    }


}
