package br.com.zup.mercadolivre.user.controller;

import br.com.zup.mercadolivre.user.dto.NewUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid NewUserRequest request) {


        return ResponseEntity.ok(request);
    }
}
