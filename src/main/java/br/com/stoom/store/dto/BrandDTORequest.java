package br.com.stoom.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandDTORequest {

    @NotEmpty
    @JsonProperty("id")
    private Long id;
    @NotEmpty
    @JsonProperty("name")
    private String name;
    @NotEmpty
    @JsonProperty("active")
    private Boolean active;
}
