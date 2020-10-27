package ro.ubb.springdi.ui;

import ro.ubb.springdi.model.Student;
import ro.ubb.springdi.service.StudentService;

import java.util.List;

/**
 * Created by radu.
 */
public class Console {
    private StudentService studentService;

    public Console(StudentService studentService) {
        this.studentService = studentService;
    }

    public void runConsole() {
        List<Student> students = studentService.getAllStudents();
        students.forEach(System.out::println);
    }
}
