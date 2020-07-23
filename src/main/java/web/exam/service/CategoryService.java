package web.exam.service;

import web.exam.model.entity.Category;
import web.exam.model.entity.CategoryNames;

public interface CategoryService {
    long getCategoriesCount();
    Category getByName(CategoryNames name);
    boolean createCategory(Category category);
}
