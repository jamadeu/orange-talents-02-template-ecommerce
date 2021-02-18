package br.com.zup.mercadolivre.purchase.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class IncreaseRankingRequest {

    @NotNull
    @Positive
    private Long purchaseId;
    @NotNull
    @Positive
    private Long sellerId;

    public IncreaseRankingRequest(@NotNull @Positive Long purchaseId, @NotNull @Positive Long sellerId) {
        this.purchaseId = purchaseId;
        this.sellerId = sellerId;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public Long getSellerId() {
        return sellerId;
    }
}
