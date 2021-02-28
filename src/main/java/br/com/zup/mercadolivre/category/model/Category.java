package br.com.zup.mercadolivre.category.model;

import br.com.zup.mercadolivre.category.repository.CategoryRepository;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;
    @NotBlank
    @Column(nullable = false, unique = true)
    @JsonProperty
    private String name;
    @ManyToOne
    @JsonProperty
    private Category motherCategory;

    @Deprecated
    public Category() {
    }

    public Category(@NotBlank String name) {
        this.name = name;
    }

    public void addMotherCategory(Category motherCategory, CategoryRepository categoryRepository) {
        if (categoryRepository.existsById(motherCategory.getId())) {
            this.motherCategory = motherCategory;
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
