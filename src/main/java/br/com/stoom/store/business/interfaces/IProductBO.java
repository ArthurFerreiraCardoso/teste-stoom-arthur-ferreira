package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.dto.ProductDTORequest;
import br.com.stoom.store.dto.ProductDTOResponse;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IProductBO {

    List<ProductDTOResponse> findAll();

    ProductDTOResponse findById(Long id);

    List<ProductDTOResponse> findByBrand(String brand);

    List<ProductDTOResponse> findByCategory(String category);

    Product updateProduct(Long id, ProductDTORequest productDTORequest) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

    Product saveProduct(ProductDTORequest productDTORequest);

    Brand validateBrand(Long brandId);

    Category validateCategory(Long categoryId);

    Product enableProduct(Long id) throws NotFoundException;

    Product disableProduct(Long id) throws NotFoundException;
}
