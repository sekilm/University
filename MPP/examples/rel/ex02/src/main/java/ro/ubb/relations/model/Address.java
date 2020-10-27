package ro.ubb.relations.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * Created by radu.
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Address {
    private String street;
    private String city;

}
