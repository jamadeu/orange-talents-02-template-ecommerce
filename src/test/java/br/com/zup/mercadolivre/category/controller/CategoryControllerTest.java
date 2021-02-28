package br.com.zup.mercadolivre.category.controller;

import br.com.zup.mercadolivre.category.dto.NewCategoryRequest;
import br.com.zup.mercadolivre.category.model.Category;
import br.com.zup.mercadolivre.category.repository.CategoryRepository;
import br.com.zup.mercadolivre.user.controller.UserCreator;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long idMotherCategory;

    @BeforeEach
    void setup() {
        userRepository.save(UserCreator.createUserPasswordEncrypted());
        Category motherCategory = categoryRepository.save(CategoryCreator.createMotherCategory());
        this.idMotherCategory = motherCategory.getId();
    }

    @Test
    @WithMockUser(username = "user@email.com", password = "123456")
    @DisplayName("create returns status code 200 when successful")
    void test1() throws Exception {
        NewCategoryRequest newCategoryRequest = CategoryCreator.createNewCategoryRequest("Category", idMotherCategory);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCategoryRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isOk()
        ).andReturn();

        Category category = categoryRepository.findByName(newCategoryRequest.getName()).orElseThrow();

        assertEquals(category.getName(), newCategoryRequest.getName());
    }

    @Test
    @WithMockUser(username = "user@email.com", password = "123456")
    @DisplayName("create returns status code 200 when idMotherCategory is null")
    void test2() throws Exception {
        NewCategoryRequest newCategoryRequest = CategoryCreator.createNewCategoryRequestIdMotherNull();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCategoryRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isOk()
        );

        Category category = categoryRepository.findByName(newCategoryRequest.getName()).orElseThrow();

        assertEquals(category.getName(), newCategoryRequest.getName());
    }

    @Test
    @WithMockUser(username = "user@email.com", password = "123456")
    @DisplayName("create returns status code 400 when idMotherCategory is invalid")
    void test3() throws Exception {
        long invalidIdMotherCategory = Math.abs(new Random().nextLong());
        NewCategoryRequest newCategoryRequest = CategoryCreator.createNewCategoryRequest("Category", invalidIdMotherCategory);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCategoryRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        ).andExpect(MockMvcResultMatchers
                .content()
                .string("Mother Category not found")
        );

        Optional<Category> optionalCategory = categoryRepository.findByName(newCategoryRequest.getName());

        assertTrue(optionalCategory.isEmpty());
    }

    @Test
    @WithMockUser(username = "user@email.com", password = "123456")
    @DisplayName("create returns status code 400 when name is invalid")
    void test4() throws Exception {
        NewCategoryRequest newCategoryRequest = CategoryCreator.createNewCategoryRequest(null, idMotherCategory);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCategoryRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        );

        Optional<Category> optionalCategory = categoryRepository.findByName(newCategoryRequest.getName());

        assertTrue(optionalCategory.isEmpty());
    }
}
