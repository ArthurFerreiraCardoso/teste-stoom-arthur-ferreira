package br.com.stoom.store.mapper;


import br.com.stoom.store.dto.CategoryDTORequest;
import br.com.stoom.store.dto.CategoryDTOResponse;
import br.com.stoom.store.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTOResponse toCategorytDTO(Category category);

    List<CategoryDTOResponse> listToCategoryDTOList(List<Category> categorytList);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "description", target = "description")
    Category toCategory(CategoryDTORequest categoryDTORequest);

}
