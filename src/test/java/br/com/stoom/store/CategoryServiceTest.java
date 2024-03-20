package br.com.stoom.store;

import br.com.stoom.store.business.CategoryService;
import br.com.stoom.store.dto.CategoryDTORequest;
import br.com.stoom.store.dto.CategoryDTOResponse;
import br.com.stoom.store.exception.NotFoundException;
import br.com.stoom.store.mapper.CategoryMapper;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void testFindAll() {
        Category category1 = new Category();
        Category category2 = new Category();
        List<Category> categories = Arrays.asList(category1, category2);

        CategoryDTOResponse categoryDTOResponse1 = new CategoryDTOResponse();
        CategoryDTOResponse categoryDTOResponse2 = new CategoryDTOResponse();
        List<CategoryDTOResponse> expectedResponse = Arrays.asList(categoryDTOResponse1, categoryDTOResponse2);

        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryMapper.listToCategoryDTOList(categories)).thenReturn(expectedResponse);

        List<CategoryDTOResponse> result = categoryService.findAll();

        assertEquals(expectedResponse, result);

        verify(categoryRepository, times(1)).findAll();

        verify(categoryMapper, times(1)).listToCategoryDTOList(categories);
    }

    @Test
    void testFindById_CategoryExists() {
        Long id = 1L;
        Category category = new Category();
        CategoryDTOResponse expectedResponse = new CategoryDTOResponse();

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(categoryMapper.toCategorytDTO(category)).thenReturn(expectedResponse);

        CategoryDTOResponse result = categoryService.findById(id);

        assertEquals(expectedResponse, result);
        verify(categoryRepository, times(1)).findById(id);
        verify(categoryMapper, times(1)).toCategorytDTO(category);
    }

    @Test
    void testFindById_CategoryNotFound() {
        Long id = 1L;

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            categoryService.findById(id);
        });

        assertEquals("Category not found with ID: " + id, exception.getMessage());
        verify(categoryRepository, times(1)).findById(id);
        verify(categoryMapper, never()).toCategorytDTO(any());
    }

    @Test
    void testSaveCategory() {
        CategoryDTORequest categoryDTORequest = new CategoryDTORequest();
        Category category = new Category();

        when(categoryMapper.toCategory(categoryDTORequest)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.saveCategory(categoryDTORequest);

        assertNotNull(result);
        verify(categoryMapper, times(1)).toCategory(categoryDTORequest);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testUpdateCategory() {
        Long id = 1L;
        CategoryDTORequest categoryDTORequest = new CategoryDTORequest();
        Category category = new Category();
        category.setActive(true);
        category.setDescription("Updated Description");

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(categoryMapper.toCategory(categoryDTORequest)).thenReturn(category);

        Category updatedCategory = categoryService.updateCategory(id, categoryDTORequest);

        assertNotNull(updatedCategory);
        assertEquals(category.getActive(), updatedCategory.getActive());
        assertEquals(category.getDescription(), updatedCategory.getDescription());
        verify(categoryRepository, times(1)).findById(id);
        verify(categoryMapper, times(1)).toCategory(categoryDTORequest);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testDeleteCategory() {
        Long id = 1L;
        Category category = new Category();
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        assertDoesNotThrow(() -> categoryService.delete(id));

        verify(categoryRepository, times(1)).findById(id);
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    void testDeleteCategory_NotFound() {
        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> categoryService.delete(id));

        verify(categoryRepository, times(1)).findById(id);
        verify(categoryRepository, never()).delete(any());
    }
}
