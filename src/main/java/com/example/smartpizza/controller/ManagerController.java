package com.example.smartpizza.controller;

import com.example.smartpizza.entity.productEntity.Product;
import com.example.smartpizza.entity.productEntity.ProductType;
import com.example.smartpizza.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


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

    @GetMapping("/take_product_by_type/type={type}")
    public String takeByProductType(ModelMap modelMap,
                                    @RequestParam("size") Optional<Integer> size,
                                    @RequestParam("page") Optional<Integer> page,
                                    @PathVariable("type") String productType) {
        Page<Product> pageable = productsService.createPageable(size, page, ProductType.valueOf(productType));
        ProductType [] productTypes = ProductType.values();
        if (pageable.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pageable.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());

            modelMap.addAttribute("pageNumbers", pageNumbers);
            modelMap.addAttribute("productType", productType);
        }
        modelMap.addAttribute("products", pageable);
        modelMap.addAttribute("productTypes",productTypes);

        return "/manager/promos";
    }

}
