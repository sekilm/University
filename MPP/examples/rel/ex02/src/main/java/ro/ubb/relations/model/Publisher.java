package ro.ubb.relations.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by radu.
 */
@Entity
@Table(name = "publisher")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Publisher implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    private Address address;

}
