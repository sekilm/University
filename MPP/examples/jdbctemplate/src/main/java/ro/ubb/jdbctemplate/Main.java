package ro.ubb.jdbctemplate;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.jdbctemplate.domain.Student;
import ro.ubb.jdbctemplate.repository.StudentRepository;

import java.util.List;

/**
 * author: radu
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "ro.ubb.jdbctemplate.config"
                );

        StudentRepository studentRepository =
                (StudentRepository) context.getBean(
                        "studentRepository");

        studentRepository.save(new Student("save", 10));

        List<Student> all = studentRepository.findAll();
        all.forEach(System.out::println);

        Long id = all.get(0).getId();
        studentRepository.update(new Student(id, "update", 8));

//        studentRepository.deleteById(id);


        System.out.println("bye");
    }
}
