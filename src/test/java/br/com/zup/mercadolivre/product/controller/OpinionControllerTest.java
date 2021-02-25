package br.com.zup.mercadolivre.product.controller;

import br.com.zup.mercadolivre.category.controller.CategoryCreator;
import br.com.zup.mercadolivre.category.model.Category;
import br.com.zup.mercadolivre.category.repository.CategoryRepository;
import br.com.zup.mercadolivre.product.dto.CharacteristicRequest;
import br.com.zup.mercadolivre.product.dto.OpinionRequest;
import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.product.model.ProductOpinion;
import br.com.zup.mercadolivre.product.repository.ProducOpinionRepository;
import br.com.zup.mercadolivre.product.repository.ProductRepository;
import br.com.zup.mercadolivre.user.controller.UserCreator;
import br.com.zup.mercadolivre.user.model.User;
import br.com.zup.mercadolivre.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
class OpinionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProducOpinionRepository producOpinionRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

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
    @DisplayName("addOpinion returns status code 200 and create a opinion")
    void test1() throws Exception {
        OpinionRequest opinionRequest = ProductRequestCreator.createOpinionRequest();
        URI uri = UriComponentsBuilder.fromPath("/product/{id}/opinion").buildAndExpand(product.getId()).toUri();

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(opinionRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isOk()
        );

        ProductOpinion productOpinion = producOpinionRepository.findByTitle(opinionRequest.getTitle()).orElseThrow();

        assertEquals(productOpinion.getProduct().getId(), product.getId());
    }

    @WithUserDetails("admin@email.com")
    @DisplayName("addOpinion returns status code 400 when rating is less than 1 or grater than 5")
    @ParameterizedTest
    @ValueSource(ints = {-10, 0, 6, 10})
    void test2(int rating) throws Exception {
        OpinionRequest opinionRequest = ProductRequestCreator.createOpinionRequest(rating, "title", "description");
        URI uri = UriComponentsBuilder.fromPath("/product/{id}/opinion").buildAndExpand(product.getId()).toUri();

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(opinionRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        ).andExpect(MockMvcResultMatchers
                .content()
                .contentType(MediaType.APPLICATION_JSON)
        );

        Optional<ProductOpinion> optionalProductOpinion = producOpinionRepository.findByTitle(opinionRequest.getTitle());

        assertTrue(optionalProductOpinion.isEmpty());
    }

    @Test
    @WithUserDetails("admin@email.com")
    @DisplayName("addOpinion returns status code 400 when title is null")
    void test3() throws Exception {
        OpinionRequest opinionRequest = ProductRequestCreator.createOpinionRequest(1, null, "description");
        URI uri = UriComponentsBuilder.fromPath("/product/{id}/opinion").buildAndExpand(product.getId()).toUri();

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(opinionRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        ).andExpect(MockMvcResultMatchers
                .content()
                .contentType(MediaType.APPLICATION_JSON)
        );

        Optional<ProductOpinion> optionalProductOpinion = producOpinionRepository.findByTitle(opinionRequest.getTitle());

        assertTrue(optionalProductOpinion.isEmpty());
    }

    @Test
    @WithUserDetails("admin@email.com")
    @DisplayName("addOpinion returns status code 400 when title is null")
    void test4() throws Exception {
        OpinionRequest opinionRequest = ProductRequestCreator.createOpinionRequest(1, "title", null);
        URI uri = UriComponentsBuilder.fromPath("/product/{id}/opinion").buildAndExpand(product.getId()).toUri();

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(opinionRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        ).andExpect(MockMvcResultMatchers
                .content()
                .contentType(MediaType.APPLICATION_JSON)
        );

        Optional<ProductOpinion> optionalProductOpinion = producOpinionRepository.findByTitle(opinionRequest.getTitle());

        assertTrue(optionalProductOpinion.isEmpty());
    }

    @Test
    @WithUserDetails("admin@email.com")
    @DisplayName("addOpinion returns status code 400 when has more than 500 characters")
    void test5() throws Exception {
        OpinionRequest opinionRequest = ProductRequestCreator.createOpinionRequest(1,
                "title",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam eu aliquet velit, eget elementum sapien. Pellentesque convallis, justo et dapibus volutpat, velit urna ornare nisl, nec sagittis risus ante euismod leo. Vivamus et lacus odio. Nam non diam tellus. Nam quis pulvinar risus. Vivamus blandit faucibus posuere. Pellentesque ut urna eros. Integer luctus feugiat mauris nec malesuada. In hac habitasse platea dictumst. Morbi tincidunt nulla sed metus molestie molestie. Phasellus elementum cras.");
        URI uri = UriComponentsBuilder.fromPath("/product/{id}/opinion").buildAndExpand(product.getId()).toUri();

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(opinionRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        ).andExpect(MockMvcResultMatchers
                .content()
                .contentType(MediaType.APPLICATION_JSON)
        );

        Optional<ProductOpinion> optionalProductOpinion = producOpinionRepository.findByTitle(opinionRequest.getTitle());

        assertTrue(optionalProductOpinion.isEmpty());
    }

}