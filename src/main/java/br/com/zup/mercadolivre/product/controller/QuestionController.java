package br.com.zup.mercadolivre.product.controller;

import br.com.zup.mercadolivre.product.dto.NewQuestionRequest;
import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.product.model.ProductQuestion;
import br.com.zup.mercadolivre.user.model.User;
import br.com.zup.mercadolivre.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
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
import java.util.List;
import java.util.Optional;

@RestController
public class QuestionController {

    @PersistenceContext
    private EntityManager em;

    private final UserRepository userRepository;

    public QuestionController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("product/{id}/question")
    @Transactional
    public ResponseEntity<?> createQuestion(@RequestBody @Valid NewQuestionRequest request, @PathVariable("id") Long productId, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> optional = userRepository.findByEmail(userDetails.getUsername());
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        User user = optional.get();
        Product product = em.find(Product.class, productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        ProductQuestion productQuestion = request.toModel(product, user);
        em.persist(productQuestion);
        System.out.println("Send email with URL to product page");
        List<ProductQuestion> questions = product.getQuestions();

        return ResponseEntity.ok(questions);

    }
}
