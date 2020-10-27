package ro.ubb.inh;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.inh.model.Battery;
import ro.ubb.inh.model.Product;
import ro.ubb.inh.repository.ProductRepository;

/**
 * Created by radu.
 */


public class Main {
    public static void main(String args[]) {
        AnnotationConfigApplicationContext context=
                new AnnotationConfigApplicationContext(
                  "ro.ubb.inh.config"
                );

        ProductRepository productRepository=
                context.getBean(ProductRepository.class);

        productRepository.save(new Product(-1l, "p1",100));
        productRepository.save(new Battery(-1l, "b1",20,true));

        productRepository.findAll()
                .forEach(System.out::println);

        System.out.println("bye");
    }

}
