package br.com.stoom.store.mapper;


import br.com.stoom.store.dto.BrandDTORequest;
import br.com.stoom.store.dto.BrandDTOResponse;
import br.com.stoom.store.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    BrandDTOResponse toBrandtDTO(Brand brand);

    List<BrandDTOResponse> listToBrandDTOResponseList(List<Brand> brandtList);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    Brand toBrand(BrandDTORequest brandDTORequest);

}
