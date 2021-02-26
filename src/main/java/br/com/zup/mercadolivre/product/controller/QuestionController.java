package br.com.zup.mercadolivre.product.controller;

import br.com.zup.mercadolivre.product.dto.NewQuestionRequest;
import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.product.model.ProductQuestion;
import br.com.zup.mercadolivre.product.repository.ProductQuestionRepository;
import br.com.zup.mercadolivre.product.repository.ProductRepository;
import br.com.zup.mercadolivre.shared.validator.annotation.IdExists;
import br.com.zup.mercadolivre.user.model.User;
import br.com.zup.mercadolivre.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
public class QuestionController {

    private final ProductQuestionRepository productQUestionRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public QuestionController(UserRepository userRepository, ProductRepository productRepository, ProductQuestionRepository productQUestionRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productQUestionRepository = productQUestionRepository;
    }

    @PostMapping("product/{id}/question")
    @Transactional
    public ResponseEntity<List<ProductQuestion>> createQuestion(
            @RequestBody @Valid NewQuestionRequest request,
            @Valid @PathVariable("id") @IdExists(domainClass = ProductQuestion.class, message = "Product not found") Long productId,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        ProductQuestion productQuestion = request.toModel(product, user);
        productQUestionRepository.save(productQuestion);
        System.out.println("Send email with URL to product page");
        List<ProductQuestion> questions = product.getQuestions();

        return ResponseEntity.ok(questions);

    }
}
