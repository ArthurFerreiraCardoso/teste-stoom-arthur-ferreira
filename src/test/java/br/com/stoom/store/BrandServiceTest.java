package br.com.stoom.store;

import br.com.stoom.store.business.BrandService;
import br.com.stoom.store.dto.BrandDTORequest;
import br.com.stoom.store.dto.BrandDTOResponse;
import br.com.stoom.store.exception.NotFoundException;
import br.com.stoom.store.mapper.BrandMapper;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.repository.BrandRepository;
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
public class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private BrandMapper brandMapper;

    @InjectMocks
    private BrandService brandService;

    @Test
    void testFindAll() {
        Brand brand1 = new Brand();
        Brand brand2 = new Brand();
        List<Brand> brandList = Arrays.asList(brand1, brand2);
        when(brandRepository.findAll()).thenReturn(brandList);

        BrandDTOResponse brandDTOResponse1 = new BrandDTOResponse();
        BrandDTOResponse brandDTOResponse2 = new BrandDTOResponse();
        List<BrandDTOResponse> expectedResponse = Arrays.asList(brandDTOResponse1, brandDTOResponse2);
        when(brandMapper.listToBrandDTOResponseList(brandList)).thenReturn(expectedResponse);

        List<BrandDTOResponse> result = brandService.findAll();

        assertEquals(expectedResponse, result);
        verify(brandRepository, times(1)).findAll();
        verify(brandMapper, times(1)).listToBrandDTOResponseList(brandList);
    }

    @Test
    void testFindById_BrandFound() {
        Long id = 1L;
        Brand brand = new Brand();
        brand.setId(id);
        BrandDTOResponse expectedResponse = new BrandDTOResponse();
        when(brandRepository.findById(id)).thenReturn(Optional.of(brand));
        when(brandMapper.toBrandtDTO(brand)).thenReturn(expectedResponse);

        BrandDTOResponse result = brandService.findById(id);

        assertEquals(expectedResponse, result);
        verify(brandRepository, times(1)).findById(id);
        verify(brandMapper, times(1)).toBrandtDTO(brand);
    }

    @Test
    void testFindById_BrandNotFound() {
        Long id = 1L;
        when(brandRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            brandService.findById(id);
        });

        assertEquals("Brand not found with ID: " + id, exception.getMessage());
        verify(brandRepository, times(1)).findById(id);
        verify(brandMapper, never()).toBrandtDTO(any());
    }

    @Test
    void testSaveBrand() {
        BrandDTORequest brandDTORequest = new BrandDTORequest();
        Brand brand = new Brand();
        Brand validatedBrand = new Brand();
        when(brandMapper.toBrand(brandDTORequest)).thenReturn(brand);
        when(brandService.validateBrand(brand)).thenReturn(validatedBrand);
        when(brandRepository.save(validatedBrand)).thenReturn(validatedBrand);

        Brand result = brandService.saveBrand(brandDTORequest);

        assertEquals(validatedBrand, result);
        verify(brandMapper, times(1)).toBrand(brandDTORequest);
        verify(brandService, times(1)).validateBrand(brand);
        verify(brandRepository, times(1)).save(validatedBrand);
    }

    @Test
    void testUpdateBrand() {
        Long id = 1L;
        BrandDTORequest brandDTORequest = new BrandDTORequest();
        Brand brand = new Brand();
        brand.setId(id);
        Brand updatedBrand = new Brand();
        when(brandRepository.findById(id)).thenReturn(Optional.of(brand));
        when(brandMapper.toBrand(brandDTORequest)).thenReturn(brand);
        when(brandRepository.save(brand)).thenReturn(updatedBrand);

        Brand result = brandService.updateBrand(id, brandDTORequest);

        assertEquals(updatedBrand, result);
        verify(brandRepository, times(1)).findById(id);
        verify(brandMapper, times(1)).toBrand(brandDTORequest);
        verify(brandRepository, times(1)).save(brand);
    }

    @Test
    void testUpdateBrand_NotFound() {
        Long id = 1L;
        BrandDTORequest brandDTORequest = new BrandDTORequest();
        when(brandRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            brandService.updateBrand(id, brandDTORequest);
        });

        verify(brandRepository, times(1)).findById(id);
        verify(brandMapper, never()).toBrand(brandDTORequest);
        verify(brandRepository, never()).save(any());
    }

    @Test
    void testDelete() {
        Long id = 1L;
        Brand brand = new Brand();
        when(brandRepository.findById(id)).thenReturn(Optional.of(brand));

        assertDoesNotThrow(() -> {
            brandService.delete(id);
        });

        verify(brandRepository, times(1)).findById(id);
        verify(brandRepository, times(1)).delete(brand);
    }

    @Test
    void testDelete_NotFound() {
        Long id = 1L;
        when(brandRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            brandService.delete(id);
        });

        verify(brandRepository, times(1)).findById(id);
        verify(brandRepository, never()).delete(any());
    }

}
