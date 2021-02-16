package br.com.zup.mercadolivre.product.dto;

import br.com.zup.mercadolivre.category.model.Category;
import br.com.zup.mercadolivre.category.repository.CategoryRepository;
import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.user.model.User;

import javax.persistence.EntityManager;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NewProductRequest {

    @NotEmpty
    private String name;
    @NotNull
    @Positive
    private BigDecimal price;
    @NotNull
    @PositiveOrZero
    private int availableQuantity;
    @NotEmpty
    private List<CharacteristicRequest> characteristics = new ArrayList<>();
    @NotEmpty
    @Size(max = 1000)
    private String description;
    @NotNull
    private Long categoryId;

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

    public Product toModel(CategoryRepository categoryRepository, User owner) {
        Category category = categoryRepository.findById(categoryId).get();
        return new Product(name, price, availableQuantity, characteristics, description, category, owner);
    }
}
