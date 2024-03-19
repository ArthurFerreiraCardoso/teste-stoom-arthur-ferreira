package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.dto.BrandDTORequest;
import br.com.stoom.store.dto.BrandDTOResponse;
import br.com.stoom.store.exception.NotFoundException;
import br.com.stoom.store.model.Brand;

import java.util.List;

public interface IBrandBO {

    List<BrandDTOResponse> findAll();

    BrandDTOResponse findById(Long id);

    Brand saveBrand(BrandDTORequest brandDTORequest);

    Brand validateBrand(Brand brand);

    Brand updateBrand(Long id, BrandDTORequest brandDTORequest) throws NotFoundException;

    void delete(Long id) throws NotFoundException;
}
