package web.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.exam.model.entity.Category;
import web.exam.model.entity.CategoryNames;
import web.exam.repository.CategoryRepository;
import web.exam.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public long getCategoriesCount() {
        return this.categoryRepository.count();
    }

    @Override
    public Category getByName(CategoryNames name) {
        return this.categoryRepository.findByName(name);
    }

    @Override
    public boolean createCategory(Category category) {
        return this.categoryRepository.saveAndFlush(category) != null;
    }
}
