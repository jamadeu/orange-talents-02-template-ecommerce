package br.com.zup.mercadolivre.user.repository;

import br.com.zup.mercadolivre.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
