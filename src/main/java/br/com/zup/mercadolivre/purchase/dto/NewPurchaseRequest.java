package br.com.zup.mercadolivre.purchase.dto;

import br.com.zup.mercadolivre.product.model.Product;
import br.com.zup.mercadolivre.purchase.model.PaymentGateway;
import br.com.zup.mercadolivre.purchase.model.Purchase;
import br.com.zup.mercadolivre.user.model.User;
import io.jsonwebtoken.lang.Assert;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NewPurchaseRequest {

    @NotNull
    private Long productId;
    @Positive
    @NotNull
    private Integer quantity;
    @NotNull
    private PaymentGateway gateway;


    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PaymentGateway getGateway() {
        return gateway;
    }

    public Purchase toModel(Product product, User buyer) {
        Assert.notNull(product);
        Assert.notNull(buyer);
        Assert.isTrue(quantity > product.getAvailableQuantity(), "Quantity unavailable");
        return new Purchase(product, quantity, buyer, gateway);
    }
}
