package com.example.smartpizza.controller;

import com.example.smartpizza.entity.productEntity.Product;
import com.example.smartpizza.entity.productEntity.ProductType;
import com.example.smartpizza.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ProductsService productsService;


    @GetMapping("")
    public String getProductPage(ModelMap modelMap) {
        ProductType [] productTypes = ProductType.values();
        modelMap.addAttribute("productTypes",productTypes);
        return "/manager/promos";
    }

    @PostMapping("")
    public String saveProductInRepo(@ModelAttribute Product product,
                                    @RequestParam("image") MultipartFile multipartFile) throws Exception {
        productsService.save(product, multipartFile);
        return "redirect:/manager";
    }

    @GetMapping("/delete_product")
    public String deleteProduct(@RequestParam("id") int id) {
        productsService.DeleteById(id);
        return "redirect:/manager";
    }

    @GetMapping("/take_product_by_type")
    public String takeByProductType(RedirectAttributes modelMap, @RequestParam("productType") String productType) {
        modelMap.addFlashAttribute("type",productType);
        modelMap.addFlashAttribute("products", productsService.searchProductByProductType(productType));
        return "redirect:/manager";
    }


}
