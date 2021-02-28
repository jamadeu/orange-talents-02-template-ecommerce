package br.com.zup.mercadolivre.product.controller;

import br.com.zup.mercadolivre.email.EmailService;
import br.com.zup.mercadolivre.email.dto.SendEmailRequest;
import br.com.zup.mercadolivre.product.dto.NewQuestionRequest;
import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.product.model.ProductQuestion;
import br.com.zup.mercadolivre.product.repository.ProductQuestionRepository;
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

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class QuestionController {

    final ProductQuestionRepository productQUestionRepository;
    final UserRepository userRepository;
    final ProductRepository productRepository;
    final EmailService emailService;

    public QuestionController(UserRepository userRepository, ProductRepository productRepository, ProductQuestionRepository productQUestionRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productQUestionRepository = productQUestionRepository;
        this.emailService = emailService;
    }

    @PostMapping("product/{id}/question")
    @Transactional
    public ResponseEntity<?> createQuestion(
            @RequestBody @Valid NewQuestionRequest request,
            @PathVariable("id") Long productId,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.badRequest().body("Product not found");
        }
        Product product = optionalProduct.get();
        ProductQuestion productQuestion = request.toModel(product, user);
        productQUestionRepository.save(productQuestion);
        SendEmailRequest sendEmailRequest = new SendEmailRequest(
                product.getOwner().getEmail(),
                "no-reply@mecadolivre.com.br",
                "New Question",
                "The product " + product.getName() + " received a question. " +
                        "http://mercadolivre.com.br/product/" + product.getId() + "/details");
        emailService.sendEmail(sendEmailRequest);
        List<ProductQuestion> questions = product.getQuestions();

        return ResponseEntity.ok(questions);
    }
}
