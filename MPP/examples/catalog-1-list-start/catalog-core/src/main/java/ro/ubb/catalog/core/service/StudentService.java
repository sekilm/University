package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();

    Student updateStudent(Long studentId, String serialNumber, String name, Integer groupNumber);

}
