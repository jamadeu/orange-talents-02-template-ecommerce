package br.com.zup.mercadolivre.product.model;

import br.com.zup.mercadolivre.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class ProductOpinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;
    @Min(value = 1, message = "Rating must be greater than or equal to 1 and less than or equal to 5 ")
    @Max(value = 5, message = "Rating must be greater than or equal to 1 and less than or equal to 5 ")
    @NotNull
    @JsonProperty
    private Byte rating;
    @NotBlank
    @JsonProperty
    private String title;
    @NotBlank
    @Size(max = 500)
    @JsonProperty
    private String description;
    @ManyToOne
    @JsonIgnore
    private Product product;
    @ManyToOne
    @JsonProperty
    private User user;

    @Deprecated
    public ProductOpinion() {
    }

    public ProductOpinion(@Min(value = 1, message = "Rating must be greater than or equal to 1 and less than or equal to 5 ") @Max(value = 5, message = "Rating must be greater than or equal to 1 and less than or equal to 5 ") @NotNull Byte rating, @NotEmpty String title, @NotEmpty @Size(max = 500) String description, Product product, User user) {
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.product = product;
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public Long getId() {
        return id;
    }

    public Byte getRating() {
        return rating;
    }

    public User getUser() {
        return user;
    }
}
