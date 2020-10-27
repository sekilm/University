package ro.ubb.springjpa.service;

import ro.ubb.springjpa.model.Student;

import java.util.List;

/**
 * Created by radu.
 */
public interface StudentService {
    List<Student> getAllStudents();

    void saveStudent(Student student);

    void updateStudent(Student student);

    void deleteById(Long id);
}
