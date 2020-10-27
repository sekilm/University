package ro.ubb.relations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.relations.model.Publisher;

/**
 * Created by radu.
 */
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
