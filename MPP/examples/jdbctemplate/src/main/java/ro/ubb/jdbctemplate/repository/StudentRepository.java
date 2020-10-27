package ro.ubb.jdbctemplate.repository;

import ro.ubb.jdbctemplate.domain.Student;

import java.util.List;

/**
 * author: radu
 */
public interface StudentRepository {
    List<Student> findAll();

    void save(Student student);

    void update(Student student);

    void deleteById(Long id);
}
