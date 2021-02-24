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
}
