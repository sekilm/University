package ro.ubb.springjpa;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.springjpa.ui.Console;


/**
 * author: radu
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("hello");

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "ro.ubb.springjpa"
                );

        context.getBean(Console.class).runConsole();

        System.out.println("bye");
    }
}
