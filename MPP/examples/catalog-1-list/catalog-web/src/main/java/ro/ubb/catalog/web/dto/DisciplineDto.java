package ro.ubb.catalog.web.dto;

import lombok.*;

/**
 * Created by radu.
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DisciplineDto extends BaseDto {
    private String name;
    private String teacher;
    private int credits;
}
