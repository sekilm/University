package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Discipline;
import ro.ubb.catalog.web.dto.DisciplineDto;

/**
 * Created by radu.
 */
@Component
public class DisciplineConverter extends AbstractConverterBaseEntityConverter<Discipline, DisciplineDto> {
    @Override
    public Discipline convertDtoToModel(DisciplineDto dto) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public DisciplineDto convertModelToDto(Discipline discipline) {
        DisciplineDto dto = DisciplineDto.builder()
                .name(discipline.getName())
                .teacher(discipline.getTeacher())
                .credits(discipline.getCredits())
                .build();
        dto.setId(discipline.getId());
        return dto;
    }
}
