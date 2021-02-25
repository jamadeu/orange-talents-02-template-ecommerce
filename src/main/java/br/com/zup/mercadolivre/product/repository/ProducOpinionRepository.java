package br.com.zup.mercadolivre.product.repository;

import br.com.zup.mercadolivre.product.model.ProductOpinion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProducOpinionRepository extends JpaRepository<ProductOpinion, Long> {
    Optional<ProductOpinion> findByTitle(String title);
}
