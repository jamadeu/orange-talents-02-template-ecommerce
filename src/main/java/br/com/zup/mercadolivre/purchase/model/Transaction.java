package br.com.zup.mercadolivre.purchase.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Positive
    private Long purchaseId;
    @NotNull
    @Positive
    private Long paymentId;
    @NotNull
    private TransactionStatus status;
    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();

    @Deprecated
    public Transaction() {
    }

    public Transaction(@NotNull @Positive Long purchaseId, @NotNull @Positive Long paymentId, @NotNull TransactionStatus status) {
        this.purchaseId = purchaseId;
        this.paymentId = paymentId;
        this.status = status;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
