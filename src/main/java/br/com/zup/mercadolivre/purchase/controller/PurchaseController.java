package br.com.zup.mercadolivre.purchase.controller;

import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.purchase.dto.NewPurchaseRequest;
import br.com.zup.mercadolivre.purchase.model.PaymentGateway;
import br.com.zup.mercadolivre.purchase.model.Purchase;
import br.com.zup.mercadolivre.user.model.User;
import br.com.zup.mercadolivre.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @PersistenceContext
    private EntityManager em;

    private final UserRepository userRepository;

    public PurchaseController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createPurchase(@RequestBody @Valid NewPurchaseRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> optional = userRepository.findByEmail(userDetails.getUsername());
        Assert.isTrue(optional.isPresent(), "Error finding logged in user");
        User buyer = optional.get();
        Product product = em.find(Product.class, request.getProductId());
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not Found");
        }
        if (request.getQuantity() > product.getAvailableQuantity()) {
            return ResponseEntity.badRequest().body("Quantity unavailable");
        }
        boolean updatedStock = product.removeFromStock(request.getQuantity());
        if (!updatedStock) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        Purchase purchase = request.toModel(product, buyer);
        em.persist(purchase);
        PaymentGateway gateway = purchase.getGateway();

        if (gateway.equals(PaymentGateway.PAYPAL)) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", "paypal.com/" + purchase.getId() + "?redirectUrl=http://localhost:8080/return-paypal/" + purchase.getId())
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", "pagseguro.com?returnId=" + purchase.getId() + "&redirectUrl=http://localhost:8080/return-pagseguro/" + purchase.getId())
                    .build();
        }
    }
}
