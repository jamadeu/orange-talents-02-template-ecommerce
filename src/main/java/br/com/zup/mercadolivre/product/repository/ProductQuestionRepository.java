package br.com.zup.mercadolivre.product.repository;

import br.com.zup.mercadolivre.product.model.ProductQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductQuestionRepository extends JpaRepository<ProductQuestion, Long> {
    Optional<ProductQuestion> findByTitle(String title);
}
