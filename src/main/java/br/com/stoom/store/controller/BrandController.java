package br.com.stoom.store.controller;

import br.com.stoom.store.business.BrandService;
import br.com.stoom.store.dto.BrandDTORequest;
import br.com.stoom.store.dto.BrandDTOResponse;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;


    @GetMapping(value = "/list-all-brands", produces = "application/json")
    public ResponseEntity<List<BrandDTOResponse>> findAll() {
        List<BrandDTOResponse> brandList = brandService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(brandList);
    }

    @GetMapping(value = "/search-brand-by-id/{id}", produces = "application/json")
    public ResponseEntity<BrandDTOResponse> findById(@PathVariable Long id) {
        BrandDTOResponse brandDTO = brandService.findById(id);

        if (brandDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(brandDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @PostMapping(value = "/save-brand", produces = "application/json")
    public ResponseEntity<BrandDTORequest> saveBrand(@RequestBody BrandDTORequest brandDTORequest) {
        try {
            brandService.saveBrand(brandDTORequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(brandDTORequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(brandDTORequest);
        }
    }

    @PutMapping(value = "/update-brand/{id}", produces = "application/json")
    public ResponseEntity<BrandDTORequest> update(@PathVariable Long id, @RequestBody BrandDTORequest brandDTORequest) throws NotFoundException {
        brandService.updateBrand(id, brandDTORequest);
        return ResponseEntity.status(HttpStatus.OK).body(brandDTORequest);
    }

    @DeleteMapping(value = "/delete-brand/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws NotFoundException {
        brandService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
