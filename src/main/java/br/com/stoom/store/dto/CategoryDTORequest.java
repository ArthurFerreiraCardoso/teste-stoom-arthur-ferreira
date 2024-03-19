package br.com.stoom.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTORequest {

    @NotEmpty
    @JsonProperty("id")
    private Long id;
    @NotEmpty
    @JsonProperty("description")
    private String description;
    @NotEmpty
    @JsonProperty("active")
    private Boolean active;
}
