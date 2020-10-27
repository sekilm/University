package ro.ubb.catalog.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Created by radu.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentsDto {
    private Set<StudentDto> students;
}
