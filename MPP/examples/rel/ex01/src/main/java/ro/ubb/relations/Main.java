package ro.ubb.relations;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.relations.model.Publisher;
import ro.ubb.relations.repository.PublisherRepository;

/**
 * Created by radu.
 */
public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.ubb.relations.config");

        PublisherRepository publisherRepository=context.getBean(PublisherRepository.class);

        Publisher publisher=Publisher.builder().name("manning").build();
        publisherRepository.save(publisher);

        System.out.println("hello");
    }
}
