package br.com.zup.mercadolivre.category.controller;

import br.com.zup.mercadolivre.category.dto.NewCategoryRequest;
import br.com.zup.mercadolivre.category.model.Category;
import br.com.zup.mercadolivre.category.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setup() {
        BDDMockito.when(categoryRepository.save(ArgumentMatchers.any(Category.class)))
                .thenReturn(CategoryCreator.createCategory());
    }

    @Test
    @DisplayName("create returns status code 200 when successful")
    void test1() {
        NewCategoryRequest newCategoryRequest = CategoryCreator.createNewCategoryRequestIdMotherNull();
        ResponseEntity<?> response = categoryController.create(newCategoryRequest);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("create returns status code 400 when category mother is not found")
    void test2() {
        NewCategoryRequest newCategoryRequest = CategoryCreator.createNewCategoryRequest(
                "Category",
                1L
        );
        ResponseEntity<?> response = categoryController.create(newCategoryRequest);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}