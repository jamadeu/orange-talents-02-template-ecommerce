package br.com.zup.mercadolivre.product.model;

import br.com.zup.mercadolivre.category.model.Category;
import br.com.zup.mercadolivre.product.dto.CharacteristicRequest;
import br.com.zup.mercadolivre.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(nullable = false)
    private String name;
    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal price;
    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private int availableQuantity;
    @NotEmpty
    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private List<ProductCharacteristic> characteristics = new ArrayList<>();
    @NotEmpty
    @Size(max = 1000)
    @Column(nullable = false)
    private String description;
    @NotNull
    @ManyToOne
    private Category category;
    @NotNull
    @ManyToOne
    private User owner;
    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Deprecated
    public Product() {
    }

    public Product(@NotEmpty String name, @NotNull @Positive BigDecimal price, @NotNull @PositiveOrZero int availableQuantity, @NotEmpty List<CharacteristicRequest> characteristics, @NotEmpty @Size(max = 1000) String description, @NotNull Category category, @NotNull User owner) {
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.description = description;
        this.category = category;
        this.owner = owner;
        this.characteristics.addAll(characteristics.stream().map(characteristic ->
                characteristic.toModel(this)).collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", availableQuantity=" + availableQuantity +
                ", characteristics=" + characteristics +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", owner=" + owner +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return availableQuantity == product.availableQuantity && Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(characteristics, product.characteristics) && Objects.equals(description, product.description) && Objects.equals(category, product.category) && Objects.equals(owner, product.owner) && Objects.equals(createdAt, product.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, availableQuantity, characteristics, description, category, owner, createdAt);
    }

    public Long getId() {
        return id;
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

    public List<ProductCharacteristic> getCharacteristics() {
        return characteristics;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public User getOwner() {
        return owner;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
