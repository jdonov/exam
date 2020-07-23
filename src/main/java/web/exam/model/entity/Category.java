package web.exam.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{
    private CategoryNames name;
    private String description;

    public Category() {
    }

    public Category(CategoryNames name, String description) {
        this.name = name;
        this.description = description;
    }

    @Column(name = "name", unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    public CategoryNames getName() {
        return name;
    }

    public void setName(CategoryNames name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
