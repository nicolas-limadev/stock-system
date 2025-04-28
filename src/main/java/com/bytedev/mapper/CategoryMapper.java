package com.bytedev.mapper;

import com.bytedev.storage.domain.Category;
import com.bytedev.storage.dto.CategoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toDto(Category category);

    Category toEntity(CategoryDTO categoryDTO);
}






