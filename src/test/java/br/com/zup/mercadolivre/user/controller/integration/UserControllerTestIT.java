package br.com.zup.mercadolivre.user.controller.integration;

import br.com.zup.mercadolivre.user.controller.UserCreator;
import br.com.zup.mercadolivre.user.dto.NewUserRequest;
import br.com.zup.mercadolivre.user.model.User;
import br.com.zup.mercadolivre.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class UserControllerTestIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("create returns status code 200 when successful")
    void test1() {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest();
        ResponseEntity<?> response = testRestTemplate.postForEntity("/user", newUserRequest, void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("create returns status code 400 when name is null")
    void test2() {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest(
                null,
                "Address",
                "949.053.040-91",
                "user@email.com",
                "123456"
        );
        ResponseEntity<?> response = testRestTemplate.postForEntity("/user", newUserRequest, void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("create returns status code 400 when address is null")
    void test3() {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest(
                "User",
                null,
                "949.053.040-91",
                "user@email.com",
                "123456"
        );
        ResponseEntity<?> response = testRestTemplate.postForEntity("/user", newUserRequest, void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("create returns status code 400 when cpf is null")
    void test4() {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest(
                "User",
                "Address",
                null,
                "user@email.com",
                "123456"
        );
        ResponseEntity<?> response = testRestTemplate.postForEntity("/user", newUserRequest, void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("create returns status code 400 when cpf is invalid")
    void test5() {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest(
                "User",
                "Address",
                "111.111.111-11",
                "user@email.com",
                "123456"
        );
        ResponseEntity<?> response = testRestTemplate.postForEntity("/user", newUserRequest, void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("create returns status code 400 when email is null")
    void test6() {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest(
                "User",
                "Address",
                "949.053.040-91",
                null,
                "123456"
        );
        ResponseEntity<?> response = testRestTemplate.postForEntity("/user", newUserRequest, void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("create returns status code 400 when email is invalid")
    void test7() {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest(
                "User",
                "Address",
                "949.053.040-91",
                "invalid",
                "123456"
        );
        ResponseEntity<?> response = testRestTemplate.postForEntity("/user", newUserRequest, void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("create returns status code 400 when password is null")
    void test8() {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest(
                "User",
                "Address",
                "949.053.040-91",
                "user@email.com",
                null
        );
        ResponseEntity<?> response = testRestTemplate.postForEntity("/user", newUserRequest, void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("create returns status code 400 when password is less than 6 characters")
    void test9() {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest(
                "User",
                "Address",
                "949.053.040-91",
                "user@email.com",
                "123"
        );
        ResponseEntity<?> response = testRestTemplate.postForEntity("/user", newUserRequest, void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("password must be saved encrypted")
    void test10() {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest();
        ResponseEntity<?> response = testRestTemplate.postForEntity("/user", newUserRequest, void.class);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = userRepository.findByEmail(newUserRequest.getEmail()).orElseThrow();

        Assertions.assertThat(encoder.matches(newUserRequest.getPassword(), user.getPassword())).isTrue();
    }

    @Test
    @DisplayName("createdAt must be in the past")
    void test11() {
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest();
        ResponseEntity<?> response = testRestTemplate.postForEntity("/user", newUserRequest, void.class);
        User user = userRepository.findByEmail(newUserRequest.getEmail()).orElseThrow();

        Assertions.assertThat(user.getCreatedAt()).isBefore(LocalDateTime.now());
    }

    @Test
    @DisplayName("create returns status code 400 when email is already in use")
    void test12() {
        User user = UserCreator.createNewUserRequest().toModel();
        userRepository.save(user);
        NewUserRequest newUserRequest = UserCreator.createNewUserRequest();
        ResponseEntity<?> response = testRestTemplate.postForEntity("/user", newUserRequest, void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


}
