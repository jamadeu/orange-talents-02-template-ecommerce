package br.com.zup.mercadolivre.product.dto;

import br.com.zup.mercadolivre.category.model.Category;
import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.user.model.User;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NewProductRequest {

    @NotBlank
    private String name;
    @NotNull
    @Positive
    private BigDecimal price;
    @NotNull
    @PositiveOrZero
    private int availableQuantity;
    @NotNull
    private List<CharacteristicRequest> characteristics = new ArrayList<>();
    @NotNull
    private List<ImageRequest> images = new ArrayList<>();
    @NotBlank
    @Size(max = 1000)
    private String description;
    @NotNull
    private Long categoryId;

    public NewProductRequest(@NotBlank String name, @NotNull @Positive BigDecimal price, @NotNull @PositiveOrZero int availableQuantity, @NotNull List<CharacteristicRequest> characteristics, @NotBlank @Size(max = 1000) String description, @NotNull Long categoryId) {
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.characteristics = characteristics;
        this.description = description;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public List<CharacteristicRequest> getCharacteristics() {
        return characteristics;
    }

    public String getDescription() {
        return description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Product toModel(Category category, User owner) {
        return new Product(name, price, availableQuantity, characteristics, description, category, owner);
    }
}
