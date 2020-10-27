package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.StudentDiscipline;
import ro.ubb.catalog.web.dto.StudentDisciplineDto;

/**
 * Created by radu.
 */
@Component
public class StudentDisciplineConverter
        extends AbstractConverter<StudentDiscipline, StudentDisciplineDto> {

    @Override
    public StudentDiscipline convertDtoToModel(StudentDisciplineDto studentDisciplineDto) {
        throw new RuntimeException("not yet implemented");
    }

    @Override
    public StudentDisciplineDto convertModelToDto(StudentDiscipline studentDiscipline) {
        return StudentDisciplineDto.builder()
                .studentId(studentDiscipline.getStudent().getId())
                .disciplineId(studentDiscipline.getDiscipline().getId())
                .disciplineName(studentDiscipline.getDiscipline().getName())
                .grade(studentDiscipline.getGrade())
                .build();
    }
}
