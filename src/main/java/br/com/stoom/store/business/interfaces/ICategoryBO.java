package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.dto.CategoryDTORequest;
import br.com.stoom.store.dto.CategoryDTOResponse;
import br.com.stoom.store.exception.NotFoundException;
import br.com.stoom.store.model.Category;

import java.util.List;

public interface ICategoryBO {

    List<CategoryDTOResponse> findAll();

    CategoryDTOResponse findById(Long id);

    Category saveCategory(CategoryDTORequest categoryDTORequest);

    Category validateCategory(Category category);

    Category updateCategory(Long id, CategoryDTORequest categoryDTORequest) throws NotFoundException;

    void delete(Long id) throws NotFoundException;
}
