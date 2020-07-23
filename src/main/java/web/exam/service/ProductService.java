package web.exam.service;

import web.exam.model.binding.ProductAddBindingModel;
import web.exam.model.entity.CategoryNames;
import web.exam.model.service.ProductServiceModel;

import java.util.List;

public interface ProductService {

    List<ProductServiceModel> getProductByCategory(CategoryNames category);

    double getTotalPrice();

    void addProduct(ProductAddBindingModel productAddBindingModel);

    void buyProduct(String id);

    void buyAllProducts();

    ProductServiceModel getByName(String name);
}
