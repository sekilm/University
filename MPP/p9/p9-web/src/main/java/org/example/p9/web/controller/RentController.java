package org.example.p9.web.controller;

import org.example.p9.core.controller.RentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.p9.web.converter.RentConverter;
import org.example.p9.web.dto.RentDto;
import org.example.p9.web.dto.RentsDto;

@RestController
public class RentController {
    public static final Logger log = LoggerFactory.getLogger(RentController.class);

    @Autowired
    private RentService rentService;

    @Autowired
    private RentConverter rentConverter;


    @RequestMapping(value = "/rents", method = RequestMethod.GET)
    RentsDto getRents() {
        log.trace("getRents --- method entered");
        return new RentsDto(rentConverter
                .convertModelsToDtos(rentService.getAllRents()));

    }

    @RequestMapping(value = "/rents", method = RequestMethod.POST)
    RentDto saveRent(@RequestBody RentDto rentDto) {
        log.trace("addRent --- method entered: rent: rent={}", rentDto);
        return rentConverter.convertModelToDto(rentService.rentMovie(
                rentConverter.convertDtoToModel(rentDto)));
    }

    @RequestMapping(value = "/rents/{id}", method = RequestMethod.PUT)
    RentDto updateRent(@PathVariable Long id,
                             @RequestBody RentDto rentDto) {
        log.trace("updateRent --- method entered: rent={}", rentDto);
        return rentConverter.convertModelToDto( rentService.updateRent(
                rentConverter.convertDtoToModel(rentDto)));
    }

    @RequestMapping(value = "/rents/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteRent(@PathVariable Long id){
        log.trace("deleteRent --- method entered: id={}", id);
        rentService.deleteRent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
