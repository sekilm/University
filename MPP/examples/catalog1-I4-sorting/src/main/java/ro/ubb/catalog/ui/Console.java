package ro.ubb.catalog.ui;

import ro.ubb.catalog.domain.Student;
import ro.ubb.catalog.service.StudentService;

import java.util.List;
import java.util.Set;

/**
 * @author radu.
 */
public class Console {
    private StudentService studentService;

    public Console(StudentService studentService) {
        this.studentService = studentService;
    }

    public void runConsole() {
//        addStudents();
        printAllStudents();
//        filterStudents();


    }


//    private void filterStudents() {
//        System.out.println("filtered students (name containing 's2'):");
//        Set<Student> students = studentService.filterStudentsByName("s2");
//        students.stream().forEach(System.out::println);
//    }

    private void printAllStudents() {
        List<Student> students = studentService.getAllStudents();
        students.stream().forEach(System.out::println);
    }


}
