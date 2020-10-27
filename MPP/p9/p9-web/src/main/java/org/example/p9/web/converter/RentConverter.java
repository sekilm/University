package org.example.p9.web.converter;

import org.example.p9.core.domain.Rent;
import org.springframework.stereotype.Component;
import org.example.p9.web.dto.RentDto;

@Component
public class RentConverter extends BaseConverter<Rent, RentDto> {
    @Override
    public Rent convertDtoToModel(RentDto dto) {
        Rent rent = Rent.builder()
                .clientId(dto.getClientId())
                .movieId(dto.getMovieId())
                .returnDate(dto.getReturnDate())
                .build();
        rent.setId(dto.getId());
        return rent;
    }

    @Override
    public RentDto convertModelToDto(Rent rent) {
        RentDto dto = RentDto.builder()
                .clientId(rent.getClientId())
                .movieId(rent.getMovieId())
                .returnDate(rent.getReturnDate())
                .build();
        dto.setId(rent.getId());
        return dto;
    }
}
