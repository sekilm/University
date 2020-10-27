package ro.ubb.remoting.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.remoting.client.service.StudentServiceClient;
import ro.ubb.remoting.common.StudentService;

/**
 * Created by radu.
 */
public class ClientApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "ro.ubb.remoting.client.config"
                );

        StudentService studentService = context.getBean(StudentService.class);

        studentService.getAllStudents()
                .forEach(System.out::println);

        System.out.println("bye");
    }
}
