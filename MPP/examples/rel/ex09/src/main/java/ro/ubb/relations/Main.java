package ro.ubb.relations;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.relations.model.Book;
import ro.ubb.relations.model.Publisher;
import ro.ubb.relations.repository.PublisherRepository;

import java.util.Arrays;

/**
 * Created by radu.
 */
public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.ubb.relations.config");

        PublisherRepository publisherRepository = context.getBean(PublisherRepository.class);

        Publisher publisher = Publisher.builder().name("manning").build();


        Book book1 = Book.builder()
                .title("b1")
                .author("a1")
                .publisher(publisher)
                .build();

        Book book2 = Book.builder()
                .title("b2")
                .author("a2")
                .publisher(publisher)
                .build();

        publisher.setBooks(Arrays.asList(book1, book2));

        publisherRepository.save(publisher);

        System.out.println("hello");
    }
}
