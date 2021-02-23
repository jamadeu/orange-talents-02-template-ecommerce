package br.com.zup.mercadolivre.category.controller;

import br.com.zup.mercadolivre.category.dto.NewCategoryRequest;
import br.com.zup.mercadolivre.category.model.Category;
import br.com.zup.mercadolivre.category.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid NewCategoryRequest request) {
        Long idMotherCategory = request.getIdMotherCategory();
        if (idMotherCategory != null && categoryRepository.findById(idMotherCategory).isEmpty()) {
            return ResponseEntity.badRequest().body("Mother Category not found");
        }
        Category category = request.toModel(categoryRepository);
        categoryRepository.save(category);
        return ResponseEntity.ok().build();
    }

}
