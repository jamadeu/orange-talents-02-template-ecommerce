package br.com.zup.mercadolivre.product.controller;

import br.com.zup.mercadolivre.category.repository.CategoryRepository;
import br.com.zup.mercadolivre.product.dto.ImageRequest;
import br.com.zup.mercadolivre.product.dto.NewProductRequest;
import br.com.zup.mercadolivre.product.dto.ProductDetailsResponse;
import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.product.repository.ProductRepository;
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

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final UploaderFake uploaderFake;
    private final CategoryRepository categoryRepository;

    public ProductController(UserRepository userRepository, UploaderFake uploaderFake, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.uploaderFake = uploaderFake;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<?> productDetails(@PathVariable("id") Long productId) {
        Product product = em.find(Product.class, productId);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        return ResponseEntity.ok(new ProductDetailsResponse(product));
    }


    @PostMapping
    @Transactional
    public ResponseEntity<Void> create(@RequestBody @Valid NewProductRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> optional = userRepository.findByEmail(userDetails.getUsername());
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        User owner = optional.get();
        Product product = request.toModel(categoryRepository, owner);
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/image")
    @Transactional
    public ResponseEntity<?> addImage(@Valid ImageRequest request, @PathVariable("id") Long productId, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> optional = userRepository.findByEmail(userDetails.getUsername());
        Assert.isTrue(optional.isPresent(), "Error finding logged in user");
        User owner = optional.get();
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Product product = optionalProduct.get();
        if (!product.getOwner().equals(owner)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<String> urls = uploaderFake.send(request.getImages());
        product.addImages(urls);
        productRepository.save(product);
        return ResponseEntity.ok(product);
    }
}
