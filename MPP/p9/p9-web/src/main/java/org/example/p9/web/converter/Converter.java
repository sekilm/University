package org.example.p9.web.converter;

import org.example.p9.core.domain.BaseEntity;
import org.example.p9.web.dto.BaseDto;

public interface Converter<Model extends BaseEntity<Long>, Dto extends BaseDto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

}

