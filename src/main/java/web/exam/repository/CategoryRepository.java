package web.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.exam.model.entity.Category;
import web.exam.model.entity.CategoryNames;


@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByName(CategoryNames name);
}
