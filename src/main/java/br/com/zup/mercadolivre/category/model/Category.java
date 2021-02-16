package br.com.zup.mercadolivre.category.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne
    private Category motherCategory;

    @Deprecated
    public Category() {
    }

    public Category(@NotEmpty String name) {
        this.name = name;
    }

    public void setMotherCategory(Category motherCategory) {
        this.motherCategory = motherCategory;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getMotherCategory() {
        return motherCategory;
    }
}
