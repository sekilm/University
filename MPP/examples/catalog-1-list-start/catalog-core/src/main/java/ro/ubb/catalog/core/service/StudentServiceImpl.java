package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;


    @Override
    public List<Student> findAll() {
        log.trace("findAll --- method entered");

        List<Student> students = studentRepository.findAll();

        log.trace("findAll: students={}", students);

        return students;
    }

    @Override
    @Transactional
    public Student updateStudent(Long studentId, String serialNumber, String name, Integer groupNumber) {
        log.trace("updateStudent: serialNumber={}, name={}, groupNumber={}", serialNumber, name, groupNumber);

        Optional<Student> student = studentRepository.findById(studentId);

        student.ifPresent(s->{
            s.setSerialNumber(serialNumber);
            s.setName(name);
            s.setGroupNumber(groupNumber);
        });

        log.trace("updateStudent: student={}", student.get());

        return student.orElse(null);
    }

}
