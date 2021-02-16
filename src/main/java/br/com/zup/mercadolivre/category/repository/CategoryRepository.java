package br.com.zup.mercadolivre.category.repository;

import br.com.zup.mercadolivre.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
