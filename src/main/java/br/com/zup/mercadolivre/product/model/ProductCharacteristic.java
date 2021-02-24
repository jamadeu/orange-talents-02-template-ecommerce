package br.com.zup.mercadolivre.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class ProductCharacteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @ManyToOne
    @JsonIgnore
    private Product product;

    @Deprecated
    public ProductCharacteristic() {
    }

    public ProductCharacteristic(@NotBlank String name, @NotBlank String description, Product product) {
        this.name = name;
        this.description = description;
        this.product = product;
    }

    public ProductCharacteristic(@NotBlank String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProductCharacteristic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCharacteristic that = (ProductCharacteristic) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, product);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Product getProduct() {
        return product;
    }
}
