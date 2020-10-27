package ro.ubb.catalog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.catalog.core.service.DisciplineService;
import ro.ubb.catalog.web.converter.DisciplineConverter;
import ro.ubb.catalog.web.dto.DisciplineDto;

import java.util.Set;

/**
 * Created by radu.
 */
@RestController
public class DisciplineController {
    //todo: log

    @Autowired
    private DisciplineService disciplineService;

    @Autowired
    private DisciplineConverter disciplineConverter;

    @RequestMapping(value = "/disciplines")
    Set<DisciplineDto> getAllDisciplines() {
        return disciplineConverter.convertModelsToDtos(
                disciplineService.getAllDisciplines()
        );
    }
}
