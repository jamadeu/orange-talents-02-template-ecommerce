package br.com.zup.mercadolivre.product.controller;

import br.com.zup.mercadolivre.product.dto.ImageRequest;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ImageController {

    final UserRepository userRepository;
    final ProductRepository productRepository;
    final UploaderFake uploaderFake;

    public ImageController(UserRepository userRepository, ProductRepository productRepository, UploaderFake uploaderFake) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.uploaderFake = uploaderFake;
    }

    @PostMapping("/product/{id}/image")
    @Transactional
    public ResponseEntity<?> addImage(@Valid ImageRequest request, @PathVariable("id") Long productId, @AuthenticationPrincipal UserDetails userDetails) {
        User owner = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
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
