package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.dto.BrandDTORequest;
import br.com.stoom.store.dto.BrandDTOResponse;
import br.com.stoom.store.exception.NotFoundException;
import br.com.stoom.store.mapper.BrandMapper;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BrandService implements IBrandBO {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private BrandRepository brandRepo;


    @Override
    public List<BrandDTOResponse> findAll() {

        return brandMapper.listToBrandDTOResponseList(brandRepo.findAll());

    }

    @Override
    public BrandDTOResponse findById(Long id) {
        Optional<Brand> brandOptional = brandRepo.findById(id);
        if (brandOptional.isPresent()) {
            Brand brand = brandOptional.get();
            return brandMapper.toBrandtDTO(brand);
        } else {
            throw new NotFoundException("Brand not found with ID: " + id);
        }
    }

    @Override
    public Brand saveBrand(BrandDTORequest brandDTORequest) {
        Brand brand = brandMapper.toBrand(brandDTORequest);

        Brand validatedBrand = this.validateBrand(brand);

        return validatedBrand;
    }

    @Override
    public Brand validateBrand(Brand brand) {
        try {
            if (Objects.isNull(brand.getId()) || brand.getId() == 0) {
                return brandRepo.save(brand);
            } else {
                Optional<Brand> optionalBrand = brandRepo.findById(brand.getId());
                if (optionalBrand.isPresent()) {
                    Brand existingBrand = optionalBrand.get();
                    throw new NotFoundException("Brand already exists in database with id: " + existingBrand.getId());
                } else {
                    throw new NotFoundException("Brand not found with id: " + brand.getId());
                }
            }
        } catch (Exception e) {
            throw new NotFoundException("Error validating Brand: " + e.getMessage(), e);
        }
    }

    @Override
    public Brand updateBrand(Long id, BrandDTORequest brandDTORequest) throws NotFoundException {
        Brand updatedBrand = brandRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));

        Brand brand = brandMapper.toBrand(brandDTORequest);

        updatedBrand.setActive(brand.getActive());
        updatedBrand.setName(brand.getName());
        return brandRepo.save(updatedBrand);
    }


    @Override
    public void delete(Long id) throws NotFoundException {
        Brand brand = brandRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));
        brandRepo.delete(brand);
    }
}
