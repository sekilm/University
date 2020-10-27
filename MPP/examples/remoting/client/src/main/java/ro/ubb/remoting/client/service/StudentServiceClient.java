package ro.ubb.remoting.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.remoting.common.Student;
import ro.ubb.remoting.common.StudentService;

import java.util.List;

/**
 * Created by radu.
 */
public class StudentServiceClient implements StudentService {
    @Autowired
    private StudentService studentService;

    @Override
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
}
