package br.com.zup.mercadolivre.product.dto;

import br.com.zup.mercadolivre.category.model.Category;
import br.com.zup.mercadolivre.category.repository.CategoryRepository;
import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.shared.validator.annotation.IdExists;
import br.com.zup.mercadolivre.user.model.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NewProductRequest {

    @JsonProperty
    @NotBlank
    final String name;
    @JsonProperty
    @NotNull
    @Positive
    final BigDecimal price;
    @JsonProperty
    @NotNull
    @PositiveOrZero
    final int availableQuantity;
    @JsonProperty
    @NotNull
    final List<CharacteristicRequest> characteristics = new ArrayList<>();
    @JsonProperty
    @NotNull
    final List<ImageRequest> images = new ArrayList<>();
    @JsonProperty
    @NotBlank
    @Size(max = 1000)
    final String description;
    @JsonProperty
    @NotNull
    @IdExists(domainClass = Category.class, message = "Category not found")
    final Long categoryId;

    @JsonCreator
    public NewProductRequest(@NotBlank String name, @NotNull @Positive BigDecimal price, @NotNull @PositiveOrZero int availableQuantity, @NotNull List<CharacteristicRequest> characteristics, @NotBlank @Size(max = 1000) String description, @NotNull Long categoryId) {
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.characteristics.addAll(characteristics);
        this.description = description;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Product toModel(CategoryRepository categoryRepository, User owner) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        return new Product(name, price, availableQuantity, characteristics, description, category, owner);
    }
}
