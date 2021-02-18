package br.com.zup.mercadolivre.purchase.model;

import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private PurchaseStatus status = PurchaseStatus.STARTED;
    @ManyToOne
    private Product product;
    @NotNull
    @Positive
    private Integer quantity;
    @ManyToOne
    private User buyer;
    @NotNull
    private PaymentGateway gateway;

    @Deprecated
    public Purchase() {
    }

    public Purchase(@NotNull Product product, @NotNull @Positive Integer quantity, User buyer, @NotNull PaymentGateway gateway) {
        this.product = product;
        this.quantity = quantity;
        this.buyer = buyer;
        this.gateway = gateway;
    }

    public Long getId() {
        return id;
    }

    public PurchaseStatus getStatus() {
        return status;
    }

    public Long getProduct() {
        return product.getId();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Long getBuyer() {
        return buyer.getId();
    }

    public PaymentGateway getGateway() {
        return gateway;
    }
}
