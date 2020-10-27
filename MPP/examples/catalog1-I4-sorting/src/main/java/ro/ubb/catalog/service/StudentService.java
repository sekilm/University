package ro.ubb.catalog.service;

import ro.ubb.catalog.domain.Student;
import ro.ubb.catalog.domain.validators.ValidatorException;
import ro.ubb.catalog.repository.sorting.SortingRepository;
import ro.ubb.catalog.repository.sorting.impl.Sort;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author radu.
 */
public class StudentService {
    private SortingRepository<Long, Student> repository;


    public StudentService(SortingRepository<Long, Student> repository) {
        this.repository = repository;
    }

    public void addStudent(Student student) throws ValidatorException {
        repository.save(Optional.ofNullable(student));
    }

    public List<Student> getAllStudents() {
        throw new RuntimeException("not yet implemented");
//        Sort sort = new Sort("name"); //sort asc by name

//        Sort sort = new Sort("group"); //sort asc by group

        //sort desc by group and desc by name (should work with any number of fields)
//        Sort sort = new Sort(Sort.Direction.DESC, "group", "name");

        //sort desc by group and asc by name
//        Sort sort = new Sort(Sort.Direction.DESC, "group")
//                .and(new Sort("name"));

//        return StreamSupport.stream(repository.findAll(sort).spliterator(), false)
//                .collect(Collectors.toList());

    }

    /**
     * Returns all students whose name contain the given string.
     *
     * @param s
     * @return
     */
    public Set<Student> filterStudentsByName(String s) {
        Iterable<Student> students = repository.findAll();
        //version 1
//        Set<Student> filteredStudents = StreamSupport.stream(students
// .spliterator(), false)
//                .filter(student -> student.getName().contains(s)).collect
// (Collectors.toSet());

        //version 2
        Set<Student> filteredStudents = new HashSet<>();
        students.forEach(filteredStudents::add);
        filteredStudents.removeIf(student -> !student.getName().contains(s));

        return filteredStudents;
    }


}
