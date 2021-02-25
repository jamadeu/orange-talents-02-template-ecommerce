package br.com.zup.mercadolivre.product.controller;

import br.com.zup.mercadolivre.category.controller.CategoryCreator;
import br.com.zup.mercadolivre.category.model.Category;
import br.com.zup.mercadolivre.category.repository.CategoryRepository;
import br.com.zup.mercadolivre.product.dto.CharacteristicRequest;
import br.com.zup.mercadolivre.product.dto.NewProductRequest;
import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.product.repository.ProductRepository;
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

import java.math.BigDecimal;
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
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

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
                .isOk()
        );

        Product product = productRepository.findByName(newProductRequest.getName()).orElseThrow();

        assertEquals(product.getCategory().getId(), newProductRequest.getCategoryId());
    }

    @Test
    @WithUserDetails("admin@email.com")
    @DisplayName("create returns 400 when name is null")
    void test2() throws Exception {
        NewProductRequest newProductRequest = ProductRequestCreator.createNewProductRequest(
                null, new BigDecimal(10), 10, characteristics, "description", category.getId());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProductRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        ).andReturn();
    }

    @Test
    @WithUserDetails("admin@email.com")
    @DisplayName("create returns 400 when value is null")
    void test3() throws Exception {
        NewProductRequest newProductRequest = ProductRequestCreator.createNewProductRequest(
                "Product", null, 10, characteristics, "description", category.getId());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProductRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        );

        Optional<Product> optionalProduct = productRepository.findByName(newProductRequest.getName());

        assertTrue(optionalProduct.isEmpty());
    }

    @Test
    @WithUserDetails("admin@email.com")
    @DisplayName("create returns 400 when value is less than 1")
    void test4() throws Exception {
        NewProductRequest newProductRequest = ProductRequestCreator.createNewProductRequest(
                "Product", new BigDecimal(0), 10, characteristics, "description", category.getId());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProductRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        );

        Optional<Product> optionalProduct = productRepository.findByName(newProductRequest.getName());

        assertTrue(optionalProduct.isEmpty());
    }

    @Test
    @WithUserDetails("admin@email.com")
    @DisplayName("create returns 400 when availableQuantity is less than 0")
    void test5() throws Exception {
        NewProductRequest newProductRequest = ProductRequestCreator.createNewProductRequest(
                "Product", new BigDecimal(10), -1, characteristics, "description", category.getId());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProductRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        );

        Optional<Product> optionalProduct = productRepository.findByName(newProductRequest.getName());

        assertTrue(optionalProduct.isEmpty());
    }

    @Test
    @WithUserDetails("admin@email.com")
    @DisplayName("create returns 400 when availableQuantity is null")
    void test6() throws Exception {
        NewProductRequest newProductRequest = ProductRequestCreator.createNewProductRequest(
                "Product", new BigDecimal(10), -1, characteristics, "description", category.getId());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProductRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        );

        Optional<Product> optionalProduct = productRepository.findByName(newProductRequest.getName());

        assertTrue(optionalProduct.isEmpty());
    }

    @Test
    @WithUserDetails("admin@email.com")
    @DisplayName("create returns 400 when description is null")
    void test7() throws Exception {
        NewProductRequest newProductRequest = ProductRequestCreator.createNewProductRequest(
                "Product", new BigDecimal(10), 1, characteristics, null, category.getId());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProductRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        );

        Optional<Product> optionalProduct = productRepository.findByName(newProductRequest.getName());

        assertTrue(optionalProduct.isEmpty());
    }

    @Test
    @WithUserDetails("admin@email.com")
    @DisplayName("create returns 400 when description has more than 1000 characters")
    void test8() throws Exception {
        NewProductRequest newProductRequest = ProductRequestCreator.createNewProductRequest(
                "Product",
                new BigDecimal(10),
                1,
                characteristics,
                "\n" +
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dapibus rhoncus est quis facilisis. Proin tempus bibendum orci nec ultricies. Morbi rutrum dui sed consectetur aliquam. Duis iaculis arcu sit amet dictum dictum. Fusce imperdiet eleifend purus, cursus tristique lorem feugiat ac. Morbi ullamcorper metus in nisi placerat ultrices. Ut nec libero elit. Duis nec scelerisque mauris. Mauris imperdiet ipsum leo, eu ultricies diam condimentum id. Maecenas viverra purus a elit semper, interdum scelerisque diam maximus.\n" +
                        "\n" +
                        "Aenean sit amet diam ac tortor porta vehicula quis non ipsum. Mauris eget dolor arcu. Pellentesque vel sapien et dolor tempor tristique vitae eu nisl. Vestibulum ullamcorper enim at purus vestibulum aliquet. Mauris consequat luctus lorem, sit amet hendrerit nibh. Interdum et malesuada fames ac ante ipsum primis in faucibus. Aliquam erat leo, placerat a venenatis sed, lobortis non nibh. Donec accumsan elit risus, ac accumsan leo sollicitudin id. Nulla vel est luctus biam.",
                category.getId());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProductRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        );

        Optional<Product> optionalProduct = productRepository.findByName(newProductRequest.getName());

        assertTrue(optionalProduct.isEmpty());
    }

    @Test
    @WithUserDetails("admin@email.com")
    @DisplayName("create returns 400 when categoryId is null")
    void test9() throws Exception {
        NewProductRequest newProductRequest = ProductRequestCreator.createNewProductRequest(
                "Product", new BigDecimal(10), 1, characteristics, "description", null);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProductRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        );

        Optional<Product> optionalProduct = productRepository.findByName(newProductRequest.getName());

        assertTrue(optionalProduct.isEmpty());
    }

    @Test
    @WithUserDetails("admin@email.com")
    @DisplayName("create returns 400 when categoryId is invalid")
    void test10() throws Exception {
        NewProductRequest newProductRequest = ProductRequestCreator.createNewProductRequest(
                "Product", new BigDecimal(10), 1, characteristics, "description", category.getId() + 1);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProductRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        );

        Optional<Product> optionalProduct = productRepository.findByName(newProductRequest.getName());

        assertTrue(optionalProduct.isEmpty());
    }
}