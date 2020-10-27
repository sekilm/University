package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.Entity;

/**
 * Created by radu.
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Student extends BaseEntity<Long>{
    private String serialNumber;
    private String name;
    private int groupNumber;
}
