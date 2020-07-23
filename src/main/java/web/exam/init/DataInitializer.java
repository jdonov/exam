package web.exam.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import web.exam.model.entity.Category;
import web.exam.model.entity.CategoryNames;
import web.exam.service.CategoryService;

import java.util.Arrays;


@Component
public class DataInitializer implements CommandLineRunner {
    private final CategoryService categoryService;

    @Autowired
    public DataInitializer(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {

        if (categoryService.getCategoriesCount() == 0) {
            Arrays.stream(CategoryNames.values())
                    .forEach(c ->
                            this.categoryService.createCategory(new Category(c, String.format("%s description", c))));

        }
    }
}
