package br.com.zup.mercadolivre.product.model;

import br.com.zup.mercadolivre.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class ProductQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(nullable = false)
    private String title;
    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne
    private User user;
    @ManyToOne
    private Product product;

    @Deprecated
    public ProductQuestion() {
    }

    public ProductQuestion(@NotEmpty String title, @NotNull User user, @NotNull Product product) {
        this.title = title;
        this.user = user;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getUser() {
        return user.getId();
    }

    public String getProduct() {
        return product.getName();
    }
}
