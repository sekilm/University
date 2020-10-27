package ro.ubb.springdi;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.springdi.ui.Console;


/**
 * Created by radu.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("hello");

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "ro.ubb.springdi.config"
                );

        Console console = context.getBean(Console.class);
        console.runConsole();

        System.out.println("bye");
    }
}
