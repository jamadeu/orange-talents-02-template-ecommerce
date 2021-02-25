package br.com.zup.mercadolivre.product.dto;

import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.product.model.ProductCharacteristic;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CharacteristicRequest {

    @NotBlank
    @JsonProperty
    private String name;
    @NotBlank
    @JsonProperty
    private String description;

    public CharacteristicRequest(@NotBlank String name, @NotBlank String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }


    public ProductCharacteristic toModel(@NotNull @Valid Product product) {
        return new ProductCharacteristic(name, description, product);
    }
}
