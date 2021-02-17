package br.com.zup.mercadolivre.product.dto;

import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.product.model.ProductOpinion;
import br.com.zup.mercadolivre.user.model.User;

import javax.validation.constraints.*;

public class OpinionRequest {

    @Min(value = 1, message = "Rating must be greater than or equal to 1 and less than or equal to 5 ")
    @Max(value = 5, message = "Rating must be greater than or equal to 1 and less than or equal to 5 ")
    @NotNull
    private Byte rating;
    @NotEmpty
    private String title;
    @NotEmpty
    @Size(max = 500)
    private String description;

    public ProductOpinion toModel(Product product, User user) {
        return new ProductOpinion(rating, title, description, product, user);
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
}
