package br.com.zup.mercadolivre.product.controller;

import br.com.zup.mercadolivre.category.controller.CategoryCreator;
import br.com.zup.mercadolivre.category.model.Category;
import br.com.zup.mercadolivre.category.repository.CategoryRepository;
import br.com.zup.mercadolivre.product.dto.CharacteristicRequest;
import br.com.zup.mercadolivre.product.dto.NewQuestionRequest;
import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.product.model.ProductQuestion;
import br.com.zup.mercadolivre.product.repository.ProductQuestionRepository;
import br.com.zup.mercadolivre.product.repository.ProductRepository;
import br.com.zup.mercadolivre.user.controller.UserCreator;
import br.com.zup.mercadolivre.user.model.User;
import br.com.zup.mercadolivre.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class QuestionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductQuestionRepository productQuestionRepository;

    private final List<CharacteristicRequest> characteristics = new ArrayList<>();
    Product product;

    @BeforeEach
    void setup() {
        Category category = categoryRepository.save(CategoryCreator.createCategory());
        characteristics.add(new CharacteristicRequest("Characteristic1", "description"));
        characteristics.add(new CharacteristicRequest("Characteristic2", "description"));
        characteristics.add(new CharacteristicRequest("Characteristic3", "description"));
        User user = userRepository.save(UserCreator.createUserPasswordEncrypted());
        product = ProductRequestCreator.createNewProductRequest(category.getId(), characteristics).toModel(categoryRepository, user);
        productRepository.save(product);
    }

    @Test
    @WithUserDetails("admin@email.com")
    @DisplayName("createQuestion returns status code 200 and create a question")
    void test1() throws Exception {
        NewQuestionRequest questionRequest = ProductRequestCreator.createQuestionRequest("Question");
        URI uri = UriComponentsBuilder.fromPath("/product/{id}/question").buildAndExpand(product.getId()).toUri();

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isOk()
        );


        ProductQuestion productQuestion = productQuestionRepository.findByTitle(questionRequest.getTitle()).orElseThrow();

        assertEquals(productQuestion.getProduct().getId(), product.getId());
    }


    @Test
    @WithUserDetails("admin@email.com")
    @DisplayName("createQuestion returns status code 400 when title is null")
    void test2() throws Exception {
        NewQuestionRequest questionRequest = ProductRequestCreator.createQuestionRequest(null);
        URI uri = UriComponentsBuilder.fromPath("/product/{id}/question").buildAndExpand(product.getId()).toUri();

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        );

        Optional<ProductQuestion> optionalProductQuestion = productQuestionRepository.findByTitle(questionRequest.getTitle());

        assertTrue(optionalProductQuestion.isEmpty());
    }

    @Test
    @WithUserDetails("admin@email.com")
    @DisplayName("createQuestion returns status code 400 when title is empty")
    void test3() throws Exception {
        NewQuestionRequest questionRequest = ProductRequestCreator.createQuestionRequest(" ");
        URI uri = UriComponentsBuilder.fromPath("/product/{id}/question").buildAndExpand(product.getId()).toUri();

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        );

        Optional<ProductQuestion> optionalProductQuestion = productQuestionRepository.findByTitle(questionRequest.getTitle());

        assertTrue(optionalProductQuestion.isEmpty());
    }

    @Test
    @WithUserDetails("admin@email.com")
    @DisplayName("createQuestion returns status code 400 when product is not found")
    void test4() throws Exception {
        NewQuestionRequest questionRequest = ProductRequestCreator.createQuestionRequest("Question");
        URI uri = UriComponentsBuilder.fromPath("/product/{id}/question").buildAndExpand(product.getId() + 1).toUri();

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        ).andDo(MockMvcResultHandlers.print());

        Optional<ProductQuestion> optionalProductQuestion = productQuestionRepository.findByTitle(questionRequest.getTitle());

        assertTrue(optionalProductQuestion.isEmpty());
    }
}