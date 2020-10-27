package ro.ubb.inh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.inh.model.Product;

/**
 * Created by radu.
 */
public interface ProductRepository
        extends JpaRepository<Product, Long> {
}
