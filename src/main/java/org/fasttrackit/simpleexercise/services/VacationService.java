package org.fasttrackit.simpleexercise.services;

import org.fasttrackit.simpleexercise.domain.Vacation;
import org.fasttrackit.simpleexercise.exceptions.ResourceNotFoundException;
import org.fasttrackit.simpleexercise.repositories.VacationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        return getAllVacations().stream()
                .filter(vacation -> vacation.getLocation().equalsIgnoreCase(location))
                .collect(Collectors.toList());
    }

    public List<Vacation> vacationsWithPriceLowerThan(Integer maxPrice) {
        return getAllVacations().stream()
                .filter(vacation -> vacation.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public Vacation add(Vacation vacation) {
        vacationRepository.save(vacation);
        return vacation;
    }

    public Vacation delete(Integer id) {
        Vacation vacationToDelete = getOrThrow(id);
        vacationRepository.delete(vacationToDelete);
        return vacationToDelete;
    }

    public Vacation replace(Integer id, Vacation vacation) {
//        Vacation vacationToReplace = updateVacation(id, vacation);   keeps the same ID and updates fields
        Vacation vacationToReplace = getOrThrow(id);
        vacationRepository.delete(vacationToReplace);       // new entry with different ID
        vacationRepository.save(vacation);
        return vacation;
    }

//    private Vacation updateVacation(Integer id, Vacation vacation) {
//        Vacation vacationToReplace = getOrThrow(id);
//        vacationToReplace.setLocation(vacation.getLocation());
//        vacationToReplace.setDuration(vacation.getDuration());
//        vacationToReplace.setPrice(vacation.getPrice());
//        return vacationToReplace;
//    }

    private Vacation getOrThrow(Integer id) {
        return vacationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find vacation with id " + id));
    }
}
