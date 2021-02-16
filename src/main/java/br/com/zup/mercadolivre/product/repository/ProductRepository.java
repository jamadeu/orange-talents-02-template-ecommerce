package br.com.zup.mercadolivre.product.repository;

import br.com.zup.mercadolivre.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
