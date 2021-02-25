package br.com.zup.mercadolivre.product.dto;

import br.com.zup.mercadolivre.category.model.Category;
import br.com.zup.mercadolivre.category.repository.CategoryRepository;
import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.user.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NewProductRequest {

    @JsonProperty
    @NotBlank
    private String name;
    @JsonProperty
    @NotNull
    @Positive
    private BigDecimal price;
    @JsonProperty
    @NotNull
    @PositiveOrZero
    private int availableQuantity;
    @JsonProperty
    @NotNull
    private List<CharacteristicRequest> characteristics = new ArrayList<>();
    @JsonProperty
    @NotNull
    private List<ImageRequest> images = new ArrayList<>();
    @JsonProperty
    @NotBlank
    @Size(max = 1000)
    private String description;
    @JsonProperty
    @NotNull
    private Long categoryId;

    @Deprecated
    public NewProductRequest() {
    }

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
