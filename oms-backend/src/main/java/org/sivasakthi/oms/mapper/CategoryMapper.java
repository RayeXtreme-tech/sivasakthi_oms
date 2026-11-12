package org.sivasakthi.oms.mapper;

import org.sivasakthi.oms.dto.CategoryDto;
import org.sivasakthi.oms.entity.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }

    public static CategoryDto toDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }
}
