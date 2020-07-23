package web.exam.model.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import web.exam.model.entity.CategoryNames;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class ProductAddBindingModel {
    private String name;
    private String description;
    private double price;
    private LocalDateTime neededBefore;
    private CategoryNames category;

    public ProductAddBindingModel() {
    }

    @Length(min = 3, max = 20, message = "Name length must be between 3 and 20 characters!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 5, message = "Description length must be minimum 5 characters!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Positive(message = "Price must be a positive number")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @NotNull(message = "The date can not be empty!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @FutureOrPresent(message = "The date cannot be in the past")
    public LocalDateTime getNeededBefore() {
        return neededBefore;
    }

    public void setNeededBefore(LocalDateTime neededBefore) {
        this.neededBefore = neededBefore;
    }

    @NotNull(message = "Category cannot be null!")
    public CategoryNames getCategory() {
        return category;
    }

    public void setCategory(CategoryNames category) {
        this.category = category;
    }
}
