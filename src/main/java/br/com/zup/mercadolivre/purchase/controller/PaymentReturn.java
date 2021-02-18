package br.com.zup.mercadolivre.purchase.controller;

import br.com.zup.mercadolivre.purchase.dto.ReturnGatewayRequest;
import org.springframework.http.ResponseEntity;

public interface PaymentReturn {

    ResponseEntity<?> processesPayment(Long purchaseId, ReturnGatewayRequest request);
}
