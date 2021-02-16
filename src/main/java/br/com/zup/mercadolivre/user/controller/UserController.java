package br.com.zup.mercadolivre.user.controller;

import br.com.zup.mercadolivre.user.dto.NewUserRequest;
import br.com.zup.mercadolivre.user.dto.UserResponse;
import br.com.zup.mercadolivre.user.model.User;
import br.com.zup.mercadolivre.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new UserResponse(optionalUser.get()));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid NewUserRequest request, UriComponentsBuilder uriBuilder) {
        User user = request.toModel();
        userRepository.save(user);
        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserResponse(user));
    }
}
