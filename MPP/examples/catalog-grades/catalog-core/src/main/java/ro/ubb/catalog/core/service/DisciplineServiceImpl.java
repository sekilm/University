package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.Discipline;
import ro.ubb.catalog.core.repository.DisciplineRepository;

import java.util.List;

/**
 * Created by radu.
 */
@Service
public class DisciplineServiceImpl implements DisciplineService {

    private static final Logger log = LoggerFactory.getLogger(DisciplineServiceImpl.class);

    @Autowired
    private DisciplineRepository disciplineRepository;


    @Override
    public List<Discipline> findAll() {
        log.trace("findAll --- method entered");

        List<Discipline> disciplines = disciplineRepository.findAll();

        log.trace("findAll: disciplines={}", disciplines);

        return disciplines;
    }
}
