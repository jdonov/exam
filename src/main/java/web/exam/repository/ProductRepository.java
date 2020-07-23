package web.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.exam.model.entity.CategoryNames;
import web.exam.model.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("SELECT p FROM Product p WHERE p.category.name = :category")
    List<Product> findAllByCategory(@Param("category") CategoryNames category);

    Optional<Product> findByName(String name);
}
