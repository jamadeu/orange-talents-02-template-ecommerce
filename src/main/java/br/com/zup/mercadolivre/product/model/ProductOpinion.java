package br.com.zup.mercadolivre.product.model;

import br.com.zup.mercadolivre.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class ProductOpinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(value = 1, message = "Rating must be greater than or equal to 1 and less than or equal to 5 ")
    @Max(value = 5, message = "Rating must be greater than or equal to 1 and less than or equal to 5 ")
    @NotNull
    private Byte rating;
    @NotEmpty
    private String title;
    @NotEmpty
    @Size(max = 500)
    private String description;
    @ManyToOne
    @JsonIgnore
    private Product product;
    @ManyToOne
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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }
}
