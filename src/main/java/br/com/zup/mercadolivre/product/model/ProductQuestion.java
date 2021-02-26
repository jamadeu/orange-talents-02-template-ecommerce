package br.com.zup.mercadolivre.product.model;

import br.com.zup.mercadolivre.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class ProductQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;
    @NotEmpty
    @Column(nullable = false)
    @JsonProperty
    private String title;
    @NotNull
    @Column(nullable = false)
    @JsonProperty
    private LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne
    @JsonProperty
    private User user;
    @ManyToOne
    @JsonIgnore
    private Product product;

    @Deprecated
    public ProductQuestion() {
    }

    public ProductQuestion(@NotEmpty String title, @NotNull User user, @NotNull Product product) {
        this.title = title;
        this.user = user;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Long getUser() {
        return user.getId();
    }

    public Product getProduct() {
        return product;
    }
}
