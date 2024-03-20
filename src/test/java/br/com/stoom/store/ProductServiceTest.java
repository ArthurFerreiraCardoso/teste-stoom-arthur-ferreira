package br.com.stoom.store;

import br.com.stoom.store.business.ProductService;
import br.com.stoom.store.dto.ProductDTORequest;
import br.com.stoom.store.dto.ProductDTOResponse;
import br.com.stoom.store.exception.NotFoundException;
import br.com.stoom.store.mapper.ProductMapper;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.repository.BrandRepository;
import br.com.stoom.store.repository.CategoryRepository;
import br.com.stoom.store.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testFindAll() {

        List<Product> productList = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDTOResponse> expectedResponse = new ArrayList<>();
        when(productMapper.listToProductDTOResponseList(productList)).thenReturn(expectedResponse);

        List<ProductDTOResponse> result = productService.findAll();

        assertEquals(expectedResponse, result);
        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(1)).listToProductDTOResponseList(productList);
    }


    @Test
    public void testFindById_ProductFound() {
        Long id = 1L;
        Product product = new Product();
        ProductDTOResponse expectedResponse = new ProductDTOResponse();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        when(productMapper.toProductDTO(product)).thenReturn(expectedResponse);

        ProductDTOResponse actualResponse = productService.findById(id);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(productRepository, times(1)).findById(id);
        verify(productMapper, times(1)).toProductDTO(product);
    }

    @Test
    public void testFindById_ProductNotFound() {
        Long id = 1L;

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        when(productMapper.toProductDTO(any())).thenThrow(new NotFoundException("Product not found with ID: " + id));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            productService.findById(id);
        });

        assertEquals("Product not found with ID: " + id, exception.getMessage());
        verify(productRepository, times(1)).findById(id);
        verify(productMapper, times(1)).toProductDTO(any());
    }

    @Test
    void testFindByBrand() {
        String brandName = "TestBrand";
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> productList = Arrays.asList(product1, product2);
        when(productRepository.findByBrandName(brandName)).thenReturn(productList);

        ProductDTOResponse productDTOResponse1 = new ProductDTOResponse();
        ProductDTOResponse productDTOResponse2 = new ProductDTOResponse();
        List<ProductDTOResponse> expectedResponse = Arrays.asList(productDTOResponse1, productDTOResponse2);
        when(productMapper.listToProductDTOResponseList(productList)).thenReturn(expectedResponse);

        List<ProductDTOResponse> result = productService.findByBrand(brandName);

        assertEquals(expectedResponse, result);
        verify(productRepository, times(1)).findByBrandName(brandName);
        verify(productMapper, times(1)).listToProductDTOResponseList(productList);
    }

    @Test
    void testFindByCategory() {
        String category = "TestCategory";
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> productList = Arrays.asList(product1, product2);
        when(productRepository.findByCategoryDescription(category)).thenReturn(productList);

        ProductDTOResponse productDTOResponse1 = new ProductDTOResponse();
        ProductDTOResponse productDTOResponse2 = new ProductDTOResponse();
        List<ProductDTOResponse> expectedResponse = Arrays.asList(productDTOResponse1, productDTOResponse2);
        when(productMapper.listToProductDTOResponseList(productList)).thenReturn(expectedResponse);

        List<ProductDTOResponse> result = productService.findByCategory(category);

        assertEquals(expectedResponse, result);
        verify(productRepository, times(1)).findByCategoryDescription(category);
        verify(productMapper, times(1)).listToProductDTOResponseList(productList);
    }

    @Test
    void testSaveProduct() {
        ProductDTORequest productDTORequest = new ProductDTORequest();
        productDTORequest.setBrandId(1L);
        productDTORequest.setCategoryId(1L);

        Brand brand = new Brand();
        when(brandRepository.findById(productDTORequest.getBrandId())).thenReturn(Optional.of(brand));

        Category category = new Category();
        when(categoryRepository.findById(productDTORequest.getCategoryId())).thenReturn(Optional.of(category));

        Product product = new Product();
        when(productMapper.toProduct(productDTORequest)).thenReturn(product);

        productService.saveProduct(productDTORequest);

        verify(brandRepository, times(1)).findById(productDTORequest.getBrandId());
        verify(categoryRepository, times(1)).findById(productDTORequest.getCategoryId());
        verify(productMapper, times(1)).toProduct(productDTORequest);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProduct_ProductFound() {
        Long id = 1L;
        ProductDTORequest productDTORequest = new ProductDTORequest();
        Product productUpdate = new Product();
        Product updatedProduct = new Product();
        when(productRepository.findById(id)).thenReturn(Optional.of(updatedProduct));
        when(productMapper.toProduct(productDTORequest)).thenReturn(productUpdate);
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.updateProduct(id, productDTORequest);

        verify(productRepository, times(1)).findById(id);
        verify(productMapper, times(1)).toProduct(productDTORequest);
        verify(productRepository, times(1)).save(updatedProduct);

        assertEquals(updatedProduct, result);
    }

    @Test
    void testUpdateProduct_ProductNotFound() {
        Long id = 1L;
        ProductDTORequest productDTORequest = new ProductDTORequest();
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            productService.updateProduct(id, productDTORequest);
        });

        verify(productRepository, times(1)).findById(id);
        verify(productMapper, never()).toProduct(any());
        verify(productRepository, never()).save(any());
    }

    @Test
    void testDelete_ProductFound() {
        Long id = 1L;
        Product product = new Product();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        assertDoesNotThrow(() -> {
            productService.delete(id);
        });

        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDelete_ProductNotFound() {
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            productService.delete(id);
        });

        assertEquals("Product not found with ID: " + id, exception.getMessage());

        verify(productRepository, times(1)).findById(id);
        verify(productRepository, never()).delete(any());
    }

    @Test
    void testEnableProduct_ProductFound() {
        Long id = 1L;
        Product product = new Product();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product result = assertDoesNotThrow(() -> {
            return productService.enableProduct(id);
        });

        assertTrue(result.getActive());
        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testEnableProduct_ProductNotFound() {
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            productService.enableProduct(id);
        });

        assertEquals("Product not found with ID: " + id, exception.getMessage());

        verify(productRepository, times(1)).findById(id);
        verify(productRepository, never()).save(any());
    }

    @Test
    void testDisableProduct_ProductFound() {
        Long id = 1L;
        Product product = new Product();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product result = assertDoesNotThrow(() -> {
            return productService.disableProduct(id);
        });

        assertFalse(result.getActive());
        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testDisableProduct_ProductNotFound() {
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            productService.disableProduct(id);
        });

        assertEquals("Product not found with ID: " + id, exception.getMessage());

        verify(productRepository, times(1)).findById(id);
        verify(productRepository, never()).save(any());
    }
}

