package br.com.zup.mercadolivre.user.controller.integration;

import br.com.zup.mercadolivre.user.controller.AuthenticationRequestCreator;
import br.com.zup.mercadolivre.user.controller.UserCreator;
import br.com.zup.mercadolivre.user.dto.AuthenticationRequest;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class AuthenticationControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final User user = UserCreator.createUserToAuthTests("user@email.com", "123456");

    @BeforeEach
    void setup() {
        userRepository.save(UserCreator.createUserPasswordEncrypted());
    }

    @Test
    @DisplayName("authenticate returns status code 200 and an instance of TokenResponse when successful")
    void test1() throws Exception {
        AuthenticationRequest authenticationRequest = AuthenticationRequestCreator.createAuthenticationRequest(user);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .is(200)
        ).andReturn();
    }

    @Test
    @DisplayName("authenticate returns status code 400 when password is wrong")
    void test2() throws Exception {
        AuthenticationRequest authenticationRequest = AuthenticationRequestCreator
                .createAuthenticationRequest(
                        UserCreator.createUserToAuthTests("user@email.com", "wrong password"));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .is(400)
        ).andReturn();
    }

    @Test
    @DisplayName("authenticate returns status code 400 when username is wrong")
    void test3() throws Exception {
        AuthenticationRequest authenticationRequest = AuthenticationRequestCreator
                .createAuthenticationRequest(
                        UserCreator.createUserToAuthTests("wrong email", "123456"));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .is(400)
        ).andReturn();
    }


}