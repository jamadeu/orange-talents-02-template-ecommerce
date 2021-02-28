package br.com.zup.mercadolivre.product.dto;

import br.com.zup.mercadolivre.product.model.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDetailsResponse {

    @JsonProperty
    private List<String> images;
    @JsonProperty
    private String productName;
    @JsonProperty
    private BigDecimal productPrice;
    @JsonProperty
    private List<ProductCharacteristic> productCharacteristics;
    @JsonProperty
    private String productDescription;
    @JsonProperty
    private Integer averageRating;
    @JsonProperty
    private Integer totalOpinions;
    @JsonProperty
    private List<ProductOpinion> productOpinions;
    @JsonProperty
    private List<ProductQuestion> productQuestions;

    @JsonCreator
    public ProductDetailsResponse(@NotNull Product product) {
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
}
