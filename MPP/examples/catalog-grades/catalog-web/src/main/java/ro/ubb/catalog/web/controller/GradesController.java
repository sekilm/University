package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.model.StudentDiscipline;
import ro.ubb.catalog.core.service.StudentService;
import ro.ubb.catalog.web.converter.StudentDisciplineConverter;
import ro.ubb.catalog.web.dto.StudentDisciplineDto;

import java.util.*;

/**
 * Created by radu.
 */

@RestController
public class GradesController {
    private static final Logger log = LoggerFactory.getLogger(GradesController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentDisciplineConverter studentDisciplineConverter;


    @RequestMapping(value = "/grades/{studentId}", method = RequestMethod.GET)
    public Set<StudentDisciplineDto> getStudentDisciplines(
            @PathVariable final Long studentId) {
        log.trace("getStudentDisciplines: studentId={}", studentId);

        throw new RuntimeException("not yet implemented");
    }

    @RequestMapping(value = "/grades/{studentId}", method = RequestMethod.PUT)
    public Set<StudentDisciplineDto> updateStudentGrades(
            @PathVariable final Long studentId,
            @RequestBody final Set<StudentDisciplineDto> studentDisciplineDtos) {
        log.trace("updateStudentGrades: studentId={}, studentDisciplineDtos={}",
                studentId, studentDisciplineDtos);

        throw new RuntimeException("not yet implemented");
    }

}
