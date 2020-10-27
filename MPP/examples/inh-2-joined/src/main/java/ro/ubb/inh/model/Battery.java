package ro.ubb.inh.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;

/**
 * Created by radu.
 */

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Battery extends Product {
    private boolean rechargeable;

    public Battery(Long id, String name, int price, boolean rechargeable) {
        super(id, name, price);

        this.rechargeable = rechargeable;
    }
}
