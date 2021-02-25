package br.com.zup.mercadolivre.user.controller;

import br.com.zup.mercadolivre.user.dto.NewUserRequest;
import br.com.zup.mercadolivre.user.model.User;
import br.com.zup.mercadolivre.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("create returns status code 200 when successful")
    void test1() throws Exception {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUserRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isOk()
        );

        Optional<User> optionalUser = userRepository.findByEmail(newUserRequest.getEmail());

        assertTrue(optionalUser.isPresent());
        assertEquals(newUserRequest.getEmail(), optionalUser.get().getEmail());
    }

    @Test
    @DisplayName("create returns status code 400 when name is null")
    void test2() throws Exception {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest(
                null,
                "Address",
                "949.053.040-91",
                "user@email.com",
                "123456"
        );

        mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUserRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        ).andExpect(MockMvcResultMatchers
                .content()
                .contentType(MediaType.APPLICATION_JSON)
        );

        Optional<User> optionalUser = userRepository.findByEmail(newUserRequest.getEmail());

        assertTrue(optionalUser.isEmpty());
    }

    @Test
    @DisplayName("create returns status code 400 when address is null")
    void test3() throws Exception {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest(
                "User",
                null,
                "949.053.040-91",
                "user@email.com",
                "123456"
        );

        mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUserRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        ).andExpect(MockMvcResultMatchers
                .content()
                .contentType(MediaType.APPLICATION_JSON)
        );

        Optional<User> optionalUser = userRepository.findByEmail(newUserRequest.getEmail());

        assertTrue(optionalUser.isEmpty());
    }

    @Test
    @DisplayName("create returns status code 400 when cpf is null")
    void test4() throws Exception {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest(
                "User",
                "Address",
                null,
                "user@email.com",
                "123456"
        );

        mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUserRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        ).andExpect(MockMvcResultMatchers
                .content()
                .contentType(MediaType.APPLICATION_JSON)
        );

        Optional<User> optionalUser = userRepository.findByEmail(newUserRequest.getEmail());

        assertTrue(optionalUser.isEmpty());
    }

    @Test
    @DisplayName("create returns status code 400 when cpf is invalid")
    void test5() throws Exception {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest(
                "User",
                "Address",
                "111.111.111-11",
                "user@email.com",
                "123456"
        );

        mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUserRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        ).andExpect(MockMvcResultMatchers
                .content()
                .contentType(MediaType.APPLICATION_JSON)
        );

        Optional<User> optionalUser = userRepository.findByEmail(newUserRequest.getEmail());

        assertTrue(optionalUser.isEmpty());
    }

    @Test
    @DisplayName("create returns status code 400 when email is null")
    void test6() throws Exception {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest(
                "User",
                "Address",
                "949.053.040-91",
                null,
                "123456"
        );

        mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUserRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        ).andExpect(MockMvcResultMatchers
                .content()
                .contentType(MediaType.APPLICATION_JSON)
        );

        Optional<User> optionalUser = userRepository.findByCpf(newUserRequest.getCpf());

        assertTrue(optionalUser.isEmpty());
    }

    @Test
    @DisplayName("create returns status code 400 when email is invalid")
    void test7() throws Exception {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest(
                "User",
                "Address",
                "949.053.040-91",
                "invalid",
                "123456"
        );

        mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUserRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        ).andExpect(MockMvcResultMatchers
                .content()
                .contentType(MediaType.APPLICATION_JSON)
        );

        Optional<User> optionalUser = userRepository.findByCpf(newUserRequest.getCpf());

        assertTrue(optionalUser.isEmpty());
    }

    @Test
    @DisplayName("create returns status code 400 when password is null")
    void test8() throws Exception {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest(
                "User",
                "Address",
                "949.053.040-91",
                "user@email.com",
                null
        );

        mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUserRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        ).andExpect(MockMvcResultMatchers
                .content()
                .contentType(MediaType.APPLICATION_JSON)
        );

        Optional<User> optionalUser = userRepository.findByCpf(newUserRequest.getCpf());

        assertTrue(optionalUser.isEmpty());
    }

    @Test
    @DisplayName("create returns status code 400 when password is less than 6 characters")
    void test9() throws Exception {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest(
                "User",
                "Address",
                "949.053.040-91",
                "user@email.com",
                "123"
        );

        mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUserRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        ).andExpect(MockMvcResultMatchers
                .content()
                .contentType(MediaType.APPLICATION_JSON)
        );

        Optional<User> optionalUser = userRepository.findByCpf(newUserRequest.getCpf());

        assertTrue(optionalUser.isEmpty());
    }

    @Test
    @DisplayName("password must be saved encrypted")
    void test10() throws Exception {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest();
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUserRequest))
        );
        User user = userRepository.findByEmail(newUserRequest.getEmail()).orElseThrow();

        assertTrue(new BCryptPasswordEncoder().matches(newUserRequest.getPassword(), user.getPassword()));
    }

    @Test
    @DisplayName("createdAt must be in the past")
    void test11() throws Exception {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest();
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUserRequest))
        );
        User user = userRepository.findByEmail(newUserRequest.getEmail()).orElseThrow();

        assertTrue(user.getCreatedAt().isBefore(LocalDateTime.now()));
    }

    @Test
    @DisplayName("create returns status code 400 when email is already in use")
    void test12() throws Exception {
        userRepository.save(UserCreator.createNewUserRequest().toModel());
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUserRequest))
        ).andExpect(MockMvcResultMatchers
                .status()
                .isBadRequest()
        ).andExpect(MockMvcResultMatchers
                .content()
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();
    }
}
