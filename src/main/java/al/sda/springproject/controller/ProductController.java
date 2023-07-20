package al.sda.springproject.controller;

import al.sda.springproject.entity.Product;
import al.sda.springproject.entity.ProductCategory;
import al.sda.springproject.service.CategoriesService;
import al.sda.springproject.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@Slf4j
public class ProductController {

    private static final String REDIRECT_PRODUCTS_VIEW = "redirect:/products";

    private final ProductService productService;
    private final CategoriesService categoriesService;

    public ProductController(ProductService productService, CategoriesService categoriesService) {
        this.productService = productService;
        this.categoriesService = categoriesService;
    }

    @GetMapping("/products")
    public String getProducts(ModelMap modelMap) {
        modelMap.addAttribute("products", this.productService.getAllProducts().get());
        return "products";
    }

    @GetMapping("/product/edit/{id}")
    public String getEditProductView(@PathVariable Long id, ModelMap modelMap) {
        log.info("Inside editProduct with id: {}", id);

        Product p = this.productService
                .findProductById(id)
                .get();
        modelMap.addAttribute("product", p);
        modelMap.addAttribute("categories", this.categoriesService.getCategories());

        return "edit-product";
    }

    @PostMapping("/product/edit/{id}")
    public String editProduct(@PathVariable Long id, @Valid Product product) {
        log.info("Inside editProduct with id: {}", id);
        this.productService.updateProduct(id, product);
        return REDIRECT_PRODUCTS_VIEW;
    }


    @GetMapping("/product/add")
    public String getAddProductView(ModelMap modelMap) {
        log.info("Inside getAddProductView");

        modelMap.addAttribute("product", new Product());
        modelMap.addAttribute("categories", this.categoriesService.getCategories());

        return "add-product";
    }

    @PostMapping("/product/add")
    public String addProduct(@Valid Product product) {
        log.info("Inside addProduct with product: {}", product.getName());
        this.productService.saveProduct(product);
        return REDIRECT_PRODUCTS_VIEW;
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        log.info("Inside deleteProduct with id: {}", id);
        this.productService.deleteProduct(id);
        return REDIRECT_PRODUCTS_VIEW;
    }

}
