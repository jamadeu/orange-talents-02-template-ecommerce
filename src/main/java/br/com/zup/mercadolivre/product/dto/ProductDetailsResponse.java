package br.com.zup.mercadolivre.product.dto;

import br.com.zup.mercadolivre.product.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDetailsResponse {

    private List<String> images;
    private String productName;
    private BigDecimal productPrice;
    private List<ProductCharacteristic> productCharacteristics;
    private String productDescription;
    private Integer averageRating;
    private Integer totalOpinions;
    private List<ProductOpinion> productOpinions;
    private List<ProductQuestion> productQuestions;

    public ProductDetailsResponse(Product product) {
        this.images = product.getImages().stream().map(ProductImage::getUrl).collect(Collectors.toList());
        this.productName = product.getName();
        this.productPrice = product.getPrice();
        this.productCharacteristics = product.getCharacteristics();
        this.productDescription = product.getDescription();
        this.averageRating = product.calcAverageRating();
        this.totalOpinions = product.getOpinions().size();
        this.productOpinions = product.getOpinions();
        this.productQuestions = product.getQuestions();
    }

    public List<String> getImages() {
        return images;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public List<ProductCharacteristic> getProductCharacteristics() {
        return productCharacteristics;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public Integer getAverageRating() {
        return averageRating;
    }

    public Integer getTotalOpinions() {
        return totalOpinions;
    }

    public List<ProductOpinion> getProductOpinions() {
        return productOpinions;
    }

    public List<ProductQuestion> getProductQuestions() {
        return productQuestions;
    }
}
