package al.sda.springproject.service;

import al.sda.springproject.dto.ServiceResponse;
import al.sda.springproject.entity.Product;
import al.sda.springproject.entity.ProductCategory;
import al.sda.springproject.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;


    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ServiceResponse<Product> saveProduct(Product product) {
        log.info("Inside saveProduct with product: {}", product);

        if ("".equals(product.getName())) {
            return ServiceResponse.ofError("Product name can't be empty.");
        }

        if (countProductsByName(product.getName()) > 0) {
            return ServiceResponse.ofError("Product already exists for product name: " + product.getName());
        }

        log.info("Returning from saveProduct");
        return ServiceResponse.of(this.productRepository.save(product));
    }

    @Override
    public ServiceResponse<Long> deleteProduct(Long id) {
        log.info("Inside deleteProduct with id: {}", id);

        if (isProductNotPresentById(id)) {
            return ServiceResponse.ofError("Couldn't find an entity to delete with that id");
        }

        this.productRepository.deleteById(id);
        log.info("Returning from deleteProduct");
        return ServiceResponse.of(id);
    }


    @Override
    public ServiceResponse<Product> updateProduct(Long id, Product product) {
        log.info("Inside updateProduct with id: {}", id);
        if (isProductNotPresentById(id)) {
            return ServiceResponse.ofError("Couldn't find a product to update with id " + id);
        }

        if (countProductsByName(product.getName()) > 1) {
            return ServiceResponse.ofError("Product already exists for product name: " + product.getName());
        }

        return ServiceResponse.of(this.productRepository.save(product));
    }

    @Override
    public ServiceResponse<List<Product>> getAllProducts() {
        return ServiceResponse
                .of(this.productRepository.findAll());
    }

    @Override
    public ServiceResponse<Product> findProductByName(String productName) {
        log.info("Inside findProductByName with productName: {}", productName);
        return this.productRepository
                .findProductByName(productName)
                .map(ServiceResponse::of)
                .orElseGet(() -> ServiceResponse.ofError("Couldn't find product with name: " + productName));
    }

    @Override
    public ServiceResponse<Product> findProductById(Long id) {
        Optional<Product> productOptional = this.productRepository.findById(id);
        return productOptional
                .map(ServiceResponse::of)
                .orElseGet(() -> ServiceResponse.ofError("Couldn't find entity with id " + id));
    }

    @Override
    public ServiceResponse<List<Product>> findProductsByCategoryName(String categoryId) {
        List<Product> productsByCategory =
                this.productRepository.findProductsByCategory(categoryId);

        if (productsByCategory == null || productsByCategory.isEmpty()) {
            return ServiceResponse.ofError("No products found with that category");
        }

        return ServiceResponse.of(productsByCategory);
    }

    private boolean isProductNotPresentById(Long id) {
        log.info("Inside isProductNotPresentById with id: {}", id);
        Optional<Product> productOptional = this.productRepository.findById(id);
        log.info("productOptional: {}", productOptional);
        return productOptional.isEmpty();
    }


    private long countProductsByName(String name) {
        return this.productRepository.countByName(name);
    }

    public void findSorted() {
        this.productRepository.findProductsByCategory("Food", Sort.by(Sort.Direction.ASC, "test"));
    }

}
