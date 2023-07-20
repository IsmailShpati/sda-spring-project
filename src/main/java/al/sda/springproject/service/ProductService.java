package al.sda.springproject.service;

import al.sda.springproject.dto.ServiceResponse;
import al.sda.springproject.entity.Product;

import java.util.List;

public interface ProductService {

    ServiceResponse<Product> saveProduct(Product product);
    ServiceResponse<Long> deleteProduct(Long id);
    ServiceResponse<Product> updateProduct(Long id, Product product);
    ServiceResponse<List<Product>> getAllProducts();
    ServiceResponse<Product> findProductByName(String productName);
    ServiceResponse<Product> findProductById(Long id);

    ServiceResponse<List<Product>> findProductsByCategoryName(String categoryId);

}
