package br.com.zup.mercadolivre.product.controller;

import br.com.zup.mercadolivre.product.dto.ImageRequest;
import br.com.zup.mercadolivre.product.dto.NewProductRequest;
import br.com.zup.mercadolivre.product.dto.ProductDetailsResponse;
import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.product.util.UploaderFake;
import br.com.zup.mercadolivre.user.model.User;
import br.com.zup.mercadolivre.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @PersistenceContext
    private EntityManager em;

    private final UserRepository userRepository;
    private final UploaderFake uploaderFake;

    public ProductController(UserRepository userRepository, UploaderFake uploaderFake) {
        this.userRepository = userRepository;
        this.uploaderFake = uploaderFake;
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<?> productDetail(@PathVariable("id") Long productId) {
        Product product = em.find(Product.class, productId);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        return ResponseEntity.ok(new ProductDetailsResponse(product));
    }


    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid NewProductRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> optional = userRepository.findByEmail(userDetails.getUsername());
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        User owner = optional.get();
        Product product = request.toModel(em, owner);
        em.persist(product);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/{id}/image")
    @Transactional
    public ResponseEntity<?> addImage(@Valid ImageRequest request, @PathVariable("id") Long productId, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> optional = userRepository.findByEmail(userDetails.getUsername());
        Assert.isTrue(optional.isPresent(), "Error finding logged in user");
        User owner = optional.get();
        Product product = em.find(Product.class, productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        if (!product.getOwner().equals(owner)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<String> urls = uploaderFake.send(request.getImages());
        product.addImages(urls);
        em.merge(product);
        return ResponseEntity.ok(product);
    }
}
