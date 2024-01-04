package bookstore.service;

import bookstore.dto.category.CategoryDto;
import bookstore.dto.category.CreateCategoryRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    CategoryDto save(CreateCategoryRequestDto categoryRequestDto);

    CategoryDto update(Long id, CreateCategoryRequestDto categoryRequestDto);

    void deleteById(Long id);

    CategoryDto getById(Long id);

    List<CategoryDto> findAll(Pageable pageable);
}
