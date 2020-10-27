package ro.ubb.catalog.web.dto;

import lombok.*;

/**
 * Created by radu.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DisciplineDto extends BaseDto {
    private String name;
    private String teacher;
    private int credits;

    @Override
    public String toString() {
        return "DisciplineDto{" +
                "name='" + name + '\'' +
                ", teacher='" + teacher + '\'' +
                ", credits=" + credits +
                "} " + super.toString();
    }
}
