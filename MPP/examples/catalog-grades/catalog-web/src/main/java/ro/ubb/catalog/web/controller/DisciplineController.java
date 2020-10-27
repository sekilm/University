package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.catalog.core.model.Discipline;
import ro.ubb.catalog.core.service.DisciplineService;
import ro.ubb.catalog.web.converter.DisciplineConverter;
import ro.ubb.catalog.web.dto.DisciplineDto;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by radu.
 */
@RestController
public class DisciplineController {

    private static final Logger log = LoggerFactory.getLogger(DisciplineController.class);

    @Autowired
    private DisciplineService disciplineService;

    @Autowired
    private DisciplineConverter disciplineConverter;


    @RequestMapping(value = "/disciplines", method = RequestMethod.GET)
    public Set<DisciplineDto> getDisciplines() {
        log.trace("getDisciplines --- method entered");

        List<Discipline> disciplines = disciplineService.findAll();

        Set<DisciplineDto> disciplineDtos =
                new HashSet<>(disciplineConverter.convertModelsToDtos(disciplines));

        log.trace("getDisciplines: disciplineDtos={}", disciplineDtos);

        return disciplineDtos;
    }
}
