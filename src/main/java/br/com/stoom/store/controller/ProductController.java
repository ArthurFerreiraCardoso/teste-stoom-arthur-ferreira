package br.com.stoom.store.controller;

import br.com.stoom.store.business.ProductService;
import br.com.stoom.store.dto.ProductDTORequest;
import br.com.stoom.store.dto.ProductDTOResponse;
import br.com.stoom.store.model.Product;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/list-all-products", produces = "application/json")
    public ResponseEntity<List<ProductDTOResponse>> findAll() {
        List<ProductDTOResponse> productList = productService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }


    @GetMapping(value = "/search-for-products-by-id/{id}", produces = "application/json")
    public ResponseEntity<ProductDTOResponse> findById(@PathVariable Long id) {
        ProductDTOResponse productDTO = productService.findById(id);

        if (productDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(productDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }


    @GetMapping(value = "/search-products-by-brand/{brand}", produces = "application/json")
    public ResponseEntity<List<ProductDTOResponse>> findByBrand(@PathVariable String brand) {
        List<ProductDTOResponse> productList = productService.findByBrand(brand);

        if (productList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping(value = "/search-products-by-category/{category}", produces = "application/json")
    public ResponseEntity<List<ProductDTOResponse>> findByCategory(@PathVariable String category) {
        List<ProductDTOResponse> productList = productService.findByCategory(category);

        if (productList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @PostMapping(value = "/save-product", produces = "application/json")
    public ResponseEntity<ProductDTORequest> saveProduct(@RequestBody ProductDTORequest productDTORequest) {
        try {
            productService.saveProduct(productDTORequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(productDTORequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productDTORequest);
        }
    }

    @PutMapping(value = "/update-product/{id}", produces = "application/json")
    public ResponseEntity<ProductDTORequest> update(@PathVariable Long id, @RequestBody ProductDTORequest productDTORequest) throws NotFoundException {
        productService.updateProduct(id, productDTORequest);
        return ResponseEntity.status(HttpStatus.OK).body(productDTORequest);
    }


    @DeleteMapping(value = "/delete-product/{id}")
    public ResponseEntity<ProductDTOResponse> delete(@PathVariable Long id) throws NotFoundException {
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PatchMapping(value = "/enable/{id}")
    public ResponseEntity<String> enableProduct(@PathVariable Long id) throws NotFoundException {
        productService.enableProduct(id);
        return new ResponseEntity<>("Product enabled successfully", HttpStatus.OK);
    }

    @PatchMapping(value = "/disable/{id}")
    public ResponseEntity<String> disableProduct(@PathVariable Long id) throws NotFoundException {
        productService.disableProduct(id);
        return new ResponseEntity<>("Product disabled successfully", HttpStatus.OK);
    }
}

