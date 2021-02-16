package br.com.zup.mercadolivre.category.controller;

import br.com.zup.mercadolivre.category.dto.NewCategoryRequest;
import br.com.zup.mercadolivre.category.model.Category;
import br.com.zup.mercadolivre.category.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalCategory.get());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid NewCategoryRequest request, UriComponentsBuilder uriBuilder) {
        Long idMotherCategory = request.getIdMotherCategory();
        if (idMotherCategory != null && categoryRepository.findById(idMotherCategory).isEmpty()) {
            return ResponseEntity.badRequest().body("Mother Category not found");
        }
        Category category = request.toModel(categoryRepository);
        categoryRepository.save(category);
        URI uri = uriBuilder.path("/category/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uri).body(category);
    }

}
