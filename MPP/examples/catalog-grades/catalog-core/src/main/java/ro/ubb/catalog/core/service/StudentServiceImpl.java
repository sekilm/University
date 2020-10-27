package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.BaseEntity;
import ro.ubb.catalog.core.model.Discipline;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.repository.DisciplineRepository;
import ro.ubb.catalog.core.repository.StudentRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;


    @Override
    public Optional<Student> findStudent(Long studentId) {
        log.trace("findStudent: studentId={}", studentId);

        Optional<Student> studentOptional = studentRepository.findById(studentId);

        log.trace("findStudent: studentOptional={}", studentOptional);

        return studentOptional;
    }

    @Override
    public List<Student> findAll() {
        log.trace("findAll --- method entered");

        List<Student> students = studentRepository.findAll();

        log.trace("findAll: students={}", students);

        return students;
    }

    @Override
    @Transactional
    public Student updateStudent(Long studentId, String serialNumber, String name, Integer groupNumber,
                                 Set<Long> disciplines) {
        log.trace("updateStudent: serialNumber={}, name={}, groupNumber={}, disciplines={}", serialNumber, name, groupNumber, disciplines);

        throw new RuntimeException("not yet implemented");
    }

    @Override
    public Student createStudent(String serialNumber, String name, Integer groupNumber) {
        log.trace("createStudent: serialNumber={}, name={}, groupNumber={}",
                serialNumber, name, groupNumber);

        throw new RuntimeException("not yet implemented");
    }

    @Override
    public void deleteStudent(Long studentId) {
        log.trace("deleteStudent: studentId={}", studentId);

        throw new RuntimeException("not yet implemented");


    }

    @Override
    @Transactional
    public Optional<Student> updateStudentGrades(Long studentId, Map<Long, Integer> grades) {
        log.trace("updateStudentGrades: studentId={}, grades={}", studentId, grades);

        throw new RuntimeException("not yet implemented");
    }

}
