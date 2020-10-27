package ro.ubb.springdi.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.springdi.model.Student;
import ro.ubb.springdi.service.StudentService;

import java.util.List;

/**
 * Created by radu.
 */
@Component
public class Console {
    @Autowired
    private StudentService studentService;

//    public Console(StudentService studentService) {
//        this.studentService = studentService;
//    }

    public void runConsole() {
        List<Student> students = studentService.getAllStudents();
        students.forEach(System.out::println);
    }
}
