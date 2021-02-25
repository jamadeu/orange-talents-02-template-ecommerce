package br.com.zup.mercadolivre.product.dto;

import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.product.model.ProductOpinion;
import br.com.zup.mercadolivre.user.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;

public class OpinionRequest {

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

    public ProductOpinion toModel(Product product, User user) {
        return new ProductOpinion(rating, title, description, product, user);
    }
}
