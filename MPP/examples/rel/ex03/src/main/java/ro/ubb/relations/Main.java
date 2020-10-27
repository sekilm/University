package ro.ubb.relations;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.relations.model.Address;
import ro.ubb.relations.model.Publisher;
import ro.ubb.relations.repository.PublisherRepository;

/**
 * Created by radu.
 */
public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.ubb.relations.config");

        PublisherRepository publisherRepository = context.getBean(PublisherRepository.class);

        Address address = Address.builder()
                .street("s1")
                .city("c1")
                .build();

        Publisher publisher = Publisher.builder()
                .name("manning")
                .address(address)
                .build();

        publisherRepository.save(publisher);

        System.out.println("bye");
    }
}
