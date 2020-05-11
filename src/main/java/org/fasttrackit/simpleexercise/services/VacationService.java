package org.fasttrackit.simpleexercise.services;

import org.fasttrackit.simpleexercise.domain.Vacation;
import org.fasttrackit.simpleexercise.exceptions.ResourceNotFoundException;
import org.fasttrackit.simpleexercise.repositories.VacationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class VacationService {

    private final VacationRepository vacationRepository;

    public VacationService(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }

    public Vacation getVacationById(Integer id) {
        return getOrThrow(id);
    }

    public List<Vacation> getAllVacations() {
        List<Vacation> vacations = new ArrayList<>();
        vacationRepository.findAll().forEach(vacations::add);
        return Collections.unmodifiableList(vacations);
    }

    public List<Vacation> vacationsInLocation(String location) {
        return StreamSupport.stream(vacationRepository.findAll().spliterator(), false)
                .filter(vacation -> vacation.getLocation().equalsIgnoreCase(location))
                .collect(Collectors.toList());
    }

    public List<Vacation> vacationsWithPriceLowerThan(Integer maxPrice) {
        return StreamSupport.stream(vacationRepository.findAll().spliterator(), false)
                .filter(vacation -> vacation.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public Vacation add(Vacation vacation) {
        return vacationRepository.save(vacation);
    }

    public Vacation delete(Integer id) {
        Vacation vacationToDelete = getOrThrow(id);
        vacationRepository.delete(vacationToDelete);
        return vacationToDelete;
    }

    public Vacation replace(Integer id, Vacation vacation) {
        Vacation vacationToReplace = getOrThrow(id);
        vacationRepository.delete(vacationToReplace);
        vacationRepository.save(vacation);
        return vacation;
    }

    private Vacation getOrThrow(Integer id) {
        return vacationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find vacation with id " + id));
    }
}
