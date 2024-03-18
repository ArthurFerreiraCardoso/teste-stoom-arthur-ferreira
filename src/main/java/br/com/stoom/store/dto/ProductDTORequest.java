package br.com.stoom.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductDTORequest {

    @NotEmpty
    @JsonProperty("description")
    private String description;

    @NotEmpty
    @JsonProperty("sku")
    private String sku;

    @JsonProperty("categoryId")
    private Long categoryId;

    @NotEmpty
    @JsonProperty("brandId")
    private Long brandId;

    @NotEmpty
    @JsonProperty("price")
    private BigDecimal price;

    @NotEmpty
    @JsonProperty("active")
    private Boolean active;

}
