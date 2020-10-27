package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Discipline;
import ro.ubb.catalog.web.dto.DisciplineDto;

/**
 * Created by radu.
 */
@Component
public class DisciplineConverter extends BaseConverter<Discipline, DisciplineDto> {
    @Override
    public Discipline convertDtoToModel(DisciplineDto dto) {

        throw new RuntimeException("not yet implemented");
    }

    @Override
    public DisciplineDto convertModelToDto(Discipline discipline) {
        DisciplineDto dto = new DisciplineDto(discipline.getName(),
                discipline.getTeacher(),
                discipline.getCredits());
        dto.setId(discipline.getId());
        return dto;
    }
}
