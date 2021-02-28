package br.com.zup.mercadolivre.product.controller;

import br.com.zup.mercadolivre.category.repository.CategoryRepository;
import br.com.zup.mercadolivre.product.dto.NewProductRequest;
import br.com.zup.mercadolivre.product.dto.ProductDetailsResponse;
import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.product.repository.ProductRepository;
import br.com.zup.mercadolivre.user.model.User;
import br.com.zup.mercadolivre.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ProductController(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<?> productDetails(@PathVariable("id") Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.badRequest().body("Product not found");
        }
        Product product = optionalProduct.get();
        return ResponseEntity.ok(new ProductDetailsResponse(product));
    }


    @PostMapping
    @Transactional
    public ResponseEntity<Void> create(@RequestBody @Valid NewProductRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        User owner = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        Product product = request.toModel(categoryRepository, owner);
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }
}
