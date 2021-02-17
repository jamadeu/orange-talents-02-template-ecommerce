package br.com.zup.mercadolivre.product.controller;

import br.com.zup.mercadolivre.product.dto.OpinionRequest;
import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.product.model.ProductOpinion;
import br.com.zup.mercadolivre.user.model.User;
import br.com.zup.mercadolivre.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class OpinionController {
    @PersistenceContext
    private EntityManager em;

    private final UserRepository userRepository;

    public OpinionController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("product/{id}/opinion")
    @Transactional
    public ResponseEntity<?> addOpinion(@RequestBody @Valid OpinionRequest request, @PathVariable("id") Long productId, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> optional = userRepository.findByEmail(userDetails.getUsername());
        Assert.isTrue(optional.isPresent(), "Error finding logged in user");
        User user = optional.get();
        Product product = em.find(Product.class, productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        ProductOpinion productOpinion = request.toModel(product, user);
        em.persist(productOpinion);
        return ResponseEntity.ok(product);
    }
}
