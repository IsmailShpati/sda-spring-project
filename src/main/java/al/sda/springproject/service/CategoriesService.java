package al.sda.springproject.service;

import al.sda.springproject.dto.ServiceResponse;
import al.sda.springproject.entity.Product;
import al.sda.springproject.entity.ProductCategory;
import al.sda.springproject.repository.ProductCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoriesService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductService productService;

    public CategoriesService(ProductCategoryRepository productCategoryRepository, ProductService productService) {
        this.productCategoryRepository = productCategoryRepository;
        this.productService = productService;
    }

    public List<ProductCategory> getCategories() {
        return this.productCategoryRepository.findAll();
    }

    public ServiceResponse<ProductCategory> deleteCategory(String categoryId) {
        Optional<ProductCategory> optionalProductCategory =
                this.productCategoryRepository.findById(categoryId);

        if (optionalProductCategory.isEmpty()) {
            return ServiceResponse.ofError("Category with id " + categoryId + " doesn't exist.");
        }

        ServiceResponse<List<Product>> response
                = this.productService.findProductsByCategoryName(categoryId);
        log.info("Response from calling findProductsByCategoryName {}", response);
        if (!response.hasErrors()) {
            return ServiceResponse.ofError("There are products using this category.");
        }

        this.productCategoryRepository.deleteById(categoryId);
        return ServiceResponse.of(optionalProductCategory.get());
    }
}
