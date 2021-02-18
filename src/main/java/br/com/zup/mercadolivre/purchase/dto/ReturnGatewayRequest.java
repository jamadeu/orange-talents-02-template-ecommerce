package br.com.zup.mercadolivre.purchase.dto;

import br.com.zup.mercadolivre.purchase.model.Transaction;
import br.com.zup.mercadolivre.purchase.model.TransactionStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ReturnGatewayRequest {

    @NotNull
    @Positive
    private Long paymentId;
    @NotEmpty
    private String status;

    public Long getPaymentId() {
        return paymentId;
    }

    public String getStatus() {
        return status;
    }

    public Transaction toModel(Long purchaseId, TransactionStatus transactionStatus) {
        return new Transaction(purchaseId, paymentId, transactionStatus);
    }
}
