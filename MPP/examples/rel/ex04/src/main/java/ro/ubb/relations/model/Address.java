package ro.ubb.relations.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by radu.
 */
//@Embeddable
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Address {
    @Id
    @GeneratedValue
    private Long id;

    private String street;

    private String city;

    @OneToOne
    private Publisher publisher;

}
