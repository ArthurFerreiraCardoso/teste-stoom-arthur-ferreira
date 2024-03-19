package br.com.stoom.store.controller;

import br.com.stoom.store.business.CategoryService;
import br.com.stoom.store.dto.CategoryDTORequest;
import br.com.stoom.store.dto.CategoryDTOResponse;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping(value = "/list-all-categories", produces = "application/json")
    public ResponseEntity<List<CategoryDTOResponse>> findAll() {
        List<CategoryDTOResponse> categoryList = categoryService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(categoryList);
    }

    @GetMapping(value = "/search-category-by-id/{id}", produces = "application/json")
    public ResponseEntity<CategoryDTOResponse> findById(@PathVariable Long id) {
        CategoryDTOResponse categoryDTO = categoryService.findById(id);

        if (categoryDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(categoryDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @PostMapping(value = "/save-category", produces = "application/json")
    public ResponseEntity<CategoryDTORequest> saveCategory(@RequestBody CategoryDTORequest categoryDTORequest) {
        try {
            categoryService.saveCategory(categoryDTORequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTORequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(categoryDTORequest);
        }
    }

    @PutMapping(value = "/update-category/{id}", produces = "application/json")
    public ResponseEntity<CategoryDTORequest> update(@PathVariable Long id, @RequestBody CategoryDTORequest categoryDTORequest) throws NotFoundException {
        categoryService.updateCategory(id, categoryDTORequest);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDTORequest);
    }

    @DeleteMapping(value = "/delete-category/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws NotFoundException {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
