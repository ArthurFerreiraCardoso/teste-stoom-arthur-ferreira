package br.com.stoom.store.mapper;


import br.com.stoom.store.dto.ProductDTORequest;
import br.com.stoom.store.dto.ProductDTOResponse;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTOResponse toProductDTO(Product product);

    List<ProductDTOResponse> listToProductDTOResponseList(List<Product> productList);

    @Mapping(source = "brandId", target = "brand.id")
    @Mapping(source = "categoryId", target = "category.id" )
    Product toProduct(ProductDTORequest productDTORequest);


}

