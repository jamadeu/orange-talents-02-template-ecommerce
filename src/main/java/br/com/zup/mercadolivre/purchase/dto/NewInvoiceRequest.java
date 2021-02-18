package br.com.zup.mercadolivre.purchase.dto;

import javax.validation.constraints.NotNull;

public class NewInvoiceRequest {

    @NotNull
    private Long purchaseId;
    @NotNull
    private Long buyerId;

    public NewInvoiceRequest(@NotNull Long purchaseId, @NotNull Long buyerId) {
        this.purchaseId = purchaseId;
        this.buyerId = buyerId;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public Long getBuyerId() {
        return buyerId;
    }
}
