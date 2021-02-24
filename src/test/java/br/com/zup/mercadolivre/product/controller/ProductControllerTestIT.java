package br.com.zup.mercadolivre.product.controller;

import br.com.zup.mercadolivre.category.controller.CategoryCreator;
import br.com.zup.mercadolivre.category.model.Category;
import br.com.zup.mercadolivre.category.repository.CategoryRepository;
import br.com.zup.mercadolivre.product.dto.CharacteristicRequest;
import br.com.zup.mercadolivre.product.dto.NewProductRequest;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class ProductControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    private final List<CharacteristicRequest> characteristics = new ArrayList<>();

    @BeforeEach
    void setup() {
        category = categoryRepository.save(CategoryCreator.createCategory());
        characteristics.add(new CharacteristicRequest("Characteristic1", "description"));
        characteristics.add(new CharacteristicRequest("Characteristic2", "description"));
        characteristics.add(new CharacteristicRequest("Characteristic3", "description"));
    }


    @Test
    @WithUserDetails("admin@email.com")
    @DisplayName("create returns 200 when successful")
    void test1() throws Exception {
        NewProductRequest newProductRequest = ProductRequestCreator.createNewProductRequest(category.getId(), characteristics);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProductRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .is(200)
        ).andReturn();
    }
}