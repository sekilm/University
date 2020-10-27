package ro.ubb.catalog.web.dto;

import lombok.*;

/**
 * Created by radu.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class StudentDisciplineDto {
    private Long studentId;
    private Long disciplineId;
    private String disciplineName;
    private Integer grade;
}
