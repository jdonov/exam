package web.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.exam.model.binding.ProductAddBindingModel;
import web.exam.model.entity.CategoryNames;
import web.exam.model.entity.Product;
import web.exam.model.service.ProductServiceModel;
import web.exam.repository.ProductRepository;
import web.exam.service.CategoryService;
import web.exam.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }

    @Override
    public List<ProductServiceModel> getProductByCategory(CategoryNames category) {
        return this.productRepository.findAllByCategory(category).stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public double getTotalPrice() {
        return this.productRepository.findAll().stream()
                .mapToDouble(p -> p.getPrice().doubleValue())
                .sum();
    }

    @Override
    public void addProduct(ProductAddBindingModel productAddBindingModel) {
        Product product = this.modelMapper.map(productAddBindingModel, Product.class);
        product.setCategory(this.categoryService.getByName(productAddBindingModel.getCategory()));
        this.productRepository.saveAndFlush(product);
    }

    @Override
    public void buyProduct(String id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public void buyAllProducts() {
        this.productRepository.deleteAll();

    }

    @Override
    public ProductServiceModel getByName(String name) {
        Product product = this.productRepository.findByName(name).orElse(null);
        if (product == null) {
            return null;
        } else {
            return this.modelMapper.map(product, ProductServiceModel.class);
        }
    }
}
