package br.com.stoom.store.dto;

import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTOResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("description")
    private String description;

    @JsonProperty("categories")
    private List<Category> categories;

    @JsonProperty("brand")
    private Brand brand;

    @JsonProperty("price")
    private BigDecimal price;
}
