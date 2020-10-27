package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.repository.StudentRepository;

import java.util.List;

/**
 * Created by radu.
 */

@Service
public class StudentServiceImpl implements StudentService {
    public static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        log.trace("getAllStudents --- method entered");

        List<Student> result = studentRepository.findAll();

        log.trace("getAllStudents: result={}", result);

        return result;
    }

    @Override
    public Student saveStudent(Student student) {
        //todo: log
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public Student updateStudent(Long id, Student student) {
        //todo log

        Student update = studentRepository.findById(id).orElse(student);
        update.setSerialNumber(student.getSerialNumber());
        update.setName(student.getName());
        update.setGroupNumber(student.getGroupNumber());

        return update;
    }

    @Override
    public void deleteStudent(Long id) {
//todo log
        studentRepository.deleteById(id);
    }
}
