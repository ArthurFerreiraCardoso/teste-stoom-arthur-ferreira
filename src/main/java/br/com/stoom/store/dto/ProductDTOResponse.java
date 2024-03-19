package br.com.stoom.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTOResponse {

    @NotEmpty
    @JsonProperty("id")
    private Long id;

    @NotEmpty
    @JsonProperty("description")
    private String description;

    @NotEmpty
    @JsonProperty("categoryId")
    private Long categoryId;

    @NotEmpty
    @JsonProperty("brandId")
    private Long brandId;

    @NotEmpty
    @JsonProperty("price")
    private BigDecimal price;
}
