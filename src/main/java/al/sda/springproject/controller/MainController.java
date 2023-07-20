package al.sda.springproject.controller;

import al.sda.springproject.entity.Product;
import al.sda.springproject.entity.ProductCategory;
import al.sda.springproject.repository.ProductCategoryRepository;
import al.sda.springproject.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public MainController(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }



}
