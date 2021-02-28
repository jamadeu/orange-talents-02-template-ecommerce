package br.com.zup.mercadolivre.category.dto;

import br.com.zup.mercadolivre.category.model.Category;
import br.com.zup.mercadolivre.category.repository.CategoryRepository;
import br.com.zup.mercadolivre.shared.validator.annotation.FieldUnique;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Optional;

public class NewCategoryRequest {

    @NotBlank
    @FieldUnique(message = "Category already exists", fieldName = "name", domainClass = Category.class)
    @JsonProperty
    final String name;
    @Positive
    @JsonProperty
    final Long idMotherCategory;

    @JsonCreator
    public NewCategoryRequest(@NotBlank String name, @Positive Long idMotherCategory) {
        this.name = name;
        this.idMotherCategory = idMotherCategory;
    }

    @Override
    public String toString() {
        return "NewCategoryRequest{" +
                "name:'" + name + '\'' +
                ", idMotherCategory:" + idMotherCategory +
                '}';
    }

    public String getName() {
        return name;
    }

    public Long getIdMotherCategory() {
        return idMotherCategory;
    }

    public Category toModel(CategoryRepository categoryRepository) {
        Category category = new Category(name);
        if (this.idMotherCategory != null) {
            Optional<Category> optionalCategory = categoryRepository.findById(idMotherCategory);
            Assert.isTrue(optionalCategory.isPresent(), "Mother Category not found");
            category.addMotherCategory(optionalCategory.get(), categoryRepository);
        }
        return category;
    }
}
