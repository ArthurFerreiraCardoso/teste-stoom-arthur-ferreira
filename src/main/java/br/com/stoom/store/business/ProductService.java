package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IProductBO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService implements IProductBO {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepo;
    @Autowired
    private CategoryRepository categoryRepo;


    @Override
    public List<ProductDTOResponse> findAll() {
        return productMapper.listToProductDTOResponseList(productRepository.findAll());
    }

    @Override
    public ProductDTOResponse findById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            return productMapper.toProductDTO(product);
        } else {
            throw new NotFoundException("Product not found with ID: " + id);
        }
    }


    @Override
    public List<ProductDTOResponse> findByBrand(String brand) {
        return productMapper.listToProductDTOResponseList(productRepository.findByBrandName(brand));
    }

    @Override
    public List<ProductDTOResponse> findByCategory(String category) {
        return productMapper.listToProductDTOResponseList(productRepository.findByCategoryDescription(category));
    }

    @Override
    public Product saveProduct(ProductDTORequest productDTORequest) {
        Product product = productMapper.toProduct(productDTORequest);

        product.setBrand(this.validateBrand(productDTORequest.getBrandId()));
        product.setCategory(this.validateCategory(productDTORequest.getCategoryId()));

        return productRepository.save(product);

    }

    @Override
    public Brand validateBrand(Long brandId) {
        Brand brand = new Brand();
        try {
            if (Objects.isNull(brandId) || brandId == 0) {
                return brandRepo.save(brand);
            } else {
                Optional<Brand> optionalBrand = brandRepo.findById(brandId);
                if (optionalBrand.isPresent()) {
                    return optionalBrand.get();
                } else {
                    throw new NotFoundException("Brand not found with id: " + brandId);
                }
            }
        } catch (Exception e) {
            throw new NotFoundException("Error validating brand: " + e.getMessage(), e);
        }
    }

    @Override
    public Category validateCategory(Long categoryId) {
        Category category = new Category();
        try {
            if (Objects.isNull(categoryId) || categoryId == 0) {
                return categoryRepo.save(category);
            } else {
                Optional<Category> optionalCategory = categoryRepo.findById(categoryId);
                if (optionalCategory.isPresent()) {
                    return optionalCategory.get();
                } else {
                    throw new NotFoundException("Brand not found with id: " + categoryId);
                }
            }
        } catch (Exception e) {
            throw new NotFoundException("Error validating category: " + e.getMessage(), e);
        }
    }

    @Override
    public Product updateProduct(Long id, ProductDTORequest productDTORequest) throws NotFoundException {
        Product updatedProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));

        Product productUpdate = productMapper.toProduct(productDTORequest);

        updatedProduct.setSku(productUpdate.getSku());
        updatedProduct.setCategory(productUpdate.getCategory());
        updatedProduct.setActive(productUpdate.getActive());
        updatedProduct.setDescription(productUpdate.getDescription());
        updatedProduct.setPrice(productUpdate.getPrice());
        updatedProduct.setBrand(productUpdate.getBrand());
        return productRepository.save(updatedProduct);

    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));
        productRepository.delete(product);
    }

    @Override
    public Product enableProduct(Long id) throws NotFoundException {
        Product productActual = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));
        productActual.setActive(true);
        return productRepository.save(productActual);
    }

    @Override
    public Product disableProduct(Long id) throws NotFoundException {
        Product productActual = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));
        productActual.setActive(false);
        return productRepository.save(productActual);
    }
}
