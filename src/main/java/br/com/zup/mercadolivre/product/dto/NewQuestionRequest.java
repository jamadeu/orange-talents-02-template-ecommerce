package br.com.zup.mercadolivre.product.dto;

import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.product.model.ProductQuestion;
import br.com.zup.mercadolivre.user.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class NewQuestionRequest {

    @NotBlank
    @JsonProperty
    private String title;

    public ProductQuestion toModel(Product product, User user) {
        return new ProductQuestion(title, user, product);
    }
}
