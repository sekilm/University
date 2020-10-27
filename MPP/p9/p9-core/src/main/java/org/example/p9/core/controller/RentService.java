package org.example.p9.core.controller;

import org.example.p9.core.domain.Rent;

import java.util.List;

public interface RentService {
    List<Rent> getAllRents();
    Rent rentMovie(Rent rent);
    void deleteRent(Long id);
    Rent updateRent(Rent rent);
}
