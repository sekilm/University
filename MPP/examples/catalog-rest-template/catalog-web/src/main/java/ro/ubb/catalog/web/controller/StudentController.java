package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.service.StudentService;
import ro.ubb.catalog.web.converter.StudentConverter;
import ro.ubb.catalog.web.dto.StudentDto;
import ro.ubb.catalog.web.dto.StudentsDto;

import java.util.List;

/**
 * Created by radu.
 */

@RestController
public class StudentController {
    public static final Logger log= LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentConverter studentConverter;


    @RequestMapping(value = "/students", method = RequestMethod.GET)
    StudentsDto getStudents() {
        //todo: log
        return new StudentsDto(studentConverter
                .convertModelsToDtos(studentService.getAllStudents()));

    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    StudentDto saveStudent(@RequestBody StudentDto studentDto) {
        //todo log
        return studentConverter.convertModelToDto(studentService.saveStudent(
                studentConverter.convertDtoToModel(studentDto)
        ));
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.PUT)
    StudentDto updateStudent(@PathVariable Long id,
                             @RequestBody StudentDto studentDto) {
        //todo: log
        return studentConverter.convertModelToDto( studentService.updateStudent(id,
                studentConverter.convertDtoToModel(studentDto)));
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteStudent(@PathVariable Long id){
        //todo:log

        studentService.deleteStudent(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
