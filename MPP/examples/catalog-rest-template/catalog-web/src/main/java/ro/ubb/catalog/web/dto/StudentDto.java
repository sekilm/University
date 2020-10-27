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
@Builder
public class StudentDto extends BaseDto {
    private String serialNumber;
    private String name;
    private int groupNumber;
}
