package br.com.zup.mercadolivre.product.dto;

import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.product.model.ProductCharacteristic;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CharacteristicRequest {

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public ProductCharacteristic toModel(@NotNull @Valid Product product) {
        return new ProductCharacteristic(name, description, product);
    }
}
