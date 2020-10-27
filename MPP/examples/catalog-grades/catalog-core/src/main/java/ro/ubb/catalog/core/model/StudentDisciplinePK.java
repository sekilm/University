package ro.ubb.catalog.core.model;

import lombok.*;

import java.io.Serializable;

/**
 * Created by radu.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
class StudentDisciplinePK implements Serializable {
    private Student student;
    private Discipline discipline;
}
