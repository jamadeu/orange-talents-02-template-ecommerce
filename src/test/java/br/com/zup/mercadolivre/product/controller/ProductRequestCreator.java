package br.com.zup.mercadolivre.product.controller;

import br.com.zup.mercadolivre.product.dto.CharacteristicRequest;
import br.com.zup.mercadolivre.product.dto.NewProductRequest;

import java.math.BigDecimal;
import java.util.List;

public class ProductRequestCreator {

    public static NewProductRequest createNewProductRequest(Long categoryId, List<CharacteristicRequest> characteristics) {
        return new NewProductRequest(
                "Product",
                new BigDecimal(20),
                10,
                characteristics,
                "Product Description",
                categoryId
        );
    }

    public static NewProductRequest createNewProductRequest(String name, BigDecimal value, int availableQuantity,
                                                            List<CharacteristicRequest> characteristics,
                                                            String description, Long categoryId) {
        return new NewProductRequest(
                name,
                value,
                availableQuantity,
                characteristics,
                description,
                categoryId
        );
    }
}
