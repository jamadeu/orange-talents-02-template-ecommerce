package br.com.zup.mercadolivre.product.dto;

import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.product.model.ProductQuestion;
import br.com.zup.mercadolivre.user.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewQuestionRequest {

    @NotBlank
    @JsonProperty
    private String title;

    @Deprecated
    public NewQuestionRequest() {
    }

    public NewQuestionRequest(@NotBlank String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public ProductQuestion toModel(@NotNull Product product, @NotNull User user) {
        return new ProductQuestion(title, user, product);
    }
}
