package br.com.zup.mercadolivre.category.controller;

import br.com.zup.mercadolivre.category.dto.NewCategoryRequest;
import br.com.zup.mercadolivre.category.model.Category;

public class CategoryCreator {

    public static Category createCategory() {
        return new Category(
                "Category"
        );
    }

    public static NewCategoryRequest createNewCategoryRequestIdMotherNull() {
        return new NewCategoryRequest(
                "Category",
                null
        );
    }

    public static NewCategoryRequest createNewCategoryRequest(String name, Long idMotherCategory) {
        return new NewCategoryRequest(
                name,
                idMotherCategory
        );
    }
}
