package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.ICategoryBO;
import br.com.stoom.store.dto.CategoryDTORequest;
import br.com.stoom.store.dto.CategoryDTOResponse;
import br.com.stoom.store.exception.NotFoundException;
import br.com.stoom.store.mapper.CategoryMapper;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryBO {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryRepository categoryRepo;


    @Override
    public List<CategoryDTOResponse> findAll() {

        return categoryMapper.listToCategoryDTOList(categoryRepo.findAll());

    }

    @Override
    public CategoryDTOResponse findById(Long id) {
        Optional<Category> categoryOptional = categoryRepo.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            return categoryMapper.toCategorytDTO(category);
        } else {
            throw new NotFoundException("Category not found with ID: " + id);
        }
    }

    @Override
    public Category saveCategory(CategoryDTORequest categoryDTORequest) {
        Category category = categoryMapper.toCategory(categoryDTORequest);

        Category validatedCategory = this.validateCategory(category);

        return validatedCategory;

    }

    @Override
    public Category validateCategory(Category category) {
        try {
            if (Objects.isNull(category.getId()) || category.getId() == 0) {
                return categoryRepo.save(category);
            } else {
                Optional<Category> optionalCategory = categoryRepo.findById(category.getId());
                if (optionalCategory.isPresent()) {
                    Category existingCategory = optionalCategory.get();
                    throw new NotFoundException("Category already exists in database with id: " + existingCategory.getId());
                } else {
                    throw new NotFoundException("Category not found with id: " + category.getId());
                }
            }
        } catch (Exception e) {
            throw new NotFoundException("Error validating category: " + e.getMessage(), e);
        }
    }

    @Override
    public Category updateCategory(Long id, CategoryDTORequest categoryDTORequest) throws NotFoundException {
        Category updatedCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + id));

        Category category = categoryMapper.toCategory(categoryDTORequest);

        updatedCategory.setActive(category.getActive());
        updatedCategory.setDescription(category.getDescription());
        return categoryRepo.save(updatedCategory);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + id));
        categoryRepo.delete(category);
    }
}
