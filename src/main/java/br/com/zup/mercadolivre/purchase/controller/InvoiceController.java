package br.com.zup.mercadolivre.purchase.controller;

import br.com.zup.mercadolivre.purchase.dto.NewInvoiceRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class InvoiceController {

    @PostMapping("/invoice")
    public ResponseEntity<String> generatesInvoice(@RequestBody @Valid NewInvoiceRequest request) {
        return ResponseEntity.ok("New invoice");
    }
}
