package br.com.zup.mercadolivre.purchase.controller;

import br.com.zup.mercadolivre.purchase.dto.IncreaseRankingRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RankingController {

    @PostMapping("/ranking")
    public ResponseEntity<String> increasesRanking(@RequestBody @Valid IncreaseRankingRequest request) {
        return ResponseEntity.ok("increased ranking");
    }
}
