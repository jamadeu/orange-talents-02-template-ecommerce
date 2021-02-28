package br.com.zup.mercadolivre.product.dto;

import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.product.model.ProductOpinion;
import br.com.zup.mercadolivre.user.model.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;

public class OpinionRequest {

    @Min(value = 1, message = "Rating must be greater than or equal to 1 and less than or equal to 5 ")
    @Max(value = 5, message = "Rating must be greater than or equal to 1 and less than or equal to 5 ")
    @NotNull
    @JsonProperty
    final int rating;
    @NotBlank
    @JsonProperty
    final String title;
    @NotBlank
    @Size(max = 500)
    @JsonProperty
    final String description;

    @JsonCreator
    public OpinionRequest(@Min(value = 1, message = "Rating must be greater than or equal to 1 and less than or equal to 5 ") @Max(value = 5, message = "Rating must be greater than or equal to 1 and less than or equal to 5 ") @NotNull int rating, @NotBlank String title, @NotBlank @Size(max = 500) String description) {
        this.rating = rating;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public ProductOpinion toModel(Product product, User user) {
        return new ProductOpinion(rating, title, description, product, user);
    }
}
