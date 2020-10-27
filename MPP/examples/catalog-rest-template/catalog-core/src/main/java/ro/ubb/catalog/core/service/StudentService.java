package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.Student;

import java.util.List;

/**
 * Created by radu.
 */
public interface StudentService {
    List<Student> getAllStudents();

    Student saveStudent(Student student);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);
}
