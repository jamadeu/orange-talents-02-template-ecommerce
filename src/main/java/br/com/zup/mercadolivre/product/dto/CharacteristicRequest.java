package br.com.zup.mercadolivre.product.dto;

import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.product.model.ProductCharacteristic;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CharacteristicRequest {

    @NotBlank
    @JsonProperty
    final String name;
    @NotBlank
    @JsonProperty
    final String description;

    @JsonCreator
    public CharacteristicRequest(@NotBlank String name, @NotBlank String description) {
        this.name = name;
        this.description = description;
    }

    public ProductCharacteristic toModel(@NotNull @Valid Product product) {
        return new ProductCharacteristic(name, description, product);
    }
}
