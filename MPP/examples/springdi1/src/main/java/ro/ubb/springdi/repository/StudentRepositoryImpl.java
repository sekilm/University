package ro.ubb.springdi.repository;

import ro.ubb.springdi.model.Student;

import java.util.Arrays;
import java.util.List;

/**
 * Created by radu.
 */
public class StudentRepositoryImpl implements StudentRepository {

    @Override
    public List<Student> findAll() {
        return Arrays.asList(
                new Student(1l, "s1", 10),
                new Student(2l, "s2", 10)
        );
    }
}
