package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Student extends BaseEntity<Long> {
    private String serialNumber;
    private String name;
    private Integer groupNumber;

    @Override
    public String toString() {
        return "Student{" +
                "serialNumber='" + serialNumber + '\'' +
                ", name='" + name + '\'' +
                ", groupNumber=" + groupNumber +
                "} " + super.toString();
    }
}
