package al.sda.springproject.repository;

import al.sda.springproject.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findProductByName(String name);

    Long countByName(String name);

    @Query("Select p From Product p Where p.category.category = :category")
    List<Product> findProductsByCategory(String category);

    List<Product> findProductsByCategory(String category, Sort sort);

}

