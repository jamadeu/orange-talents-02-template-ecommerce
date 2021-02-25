package br.com.zup.mercadolivre.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;
    @NotNull
    @URL
    @Column(nullable = false, unique = true)
    @JsonProperty
    private String url;
    @ManyToOne
    @JsonIgnore
    private Product product;

    @Deprecated
    public ProductImage() {
    }

    public ProductImage(@URL String url, Product product) {
        this.url = url;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Product getProduct() {
        return product;
    }
}
