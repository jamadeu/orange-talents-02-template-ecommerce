package br.com.zup.mercadolivre.product.controller;

import br.com.zup.mercadolivre.product.dto.OpinionRequest;
import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.product.model.ProductOpinion;
import br.com.zup.mercadolivre.product.repository.ProducOpinionRepository;
import br.com.zup.mercadolivre.product.repository.ProductRepository;
import br.com.zup.mercadolivre.user.model.User;
import br.com.zup.mercadolivre.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    final ProductRepository productRepository;
    final ProducOpinionRepository producOpinionRepository;

    public OpinionController(UserRepository userRepository, ProductRepository productRepository, ProducOpinionRepository producOpinionRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.producOpinionRepository = producOpinionRepository;
    }

    @PostMapping("product/{id}/opinion")
    @Transactional
    public ResponseEntity<Void> addOpinion(@RequestBody @Valid OpinionRequest request, @PathVariable("id") Long productId, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Product product = optionalProduct.get();
        ProductOpinion productOpinion = request.toModel(product, user);
        producOpinionRepository.save(productOpinion);
        return ResponseEntity.ok().build();
    }
}
