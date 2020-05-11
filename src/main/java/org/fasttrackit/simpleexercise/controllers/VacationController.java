package org.fasttrackit.simpleexercise.controllers;

import org.fasttrackit.simpleexercise.domain.Vacation;
import org.fasttrackit.simpleexercise.services.VacationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vacations")
public class VacationController {

    private final VacationService vacationService;

    public VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @GetMapping
    public List<Vacation> getAll(@RequestParam(required = false) Integer maxPrice,
                                 @RequestParam(required = false) String location) {
        if (maxPrice == null && location == null) {
            return vacationService.getAllVacations();
        } else if (maxPrice != null && location == null) {
            return vacationService.vacationsWithPriceLowerThan(maxPrice);
        } else {
            return vacationService.vacationsInLocation(location);
        }
    }

    @GetMapping("{id}")
    public Vacation getVacationById(@PathVariable(required = false) Integer id) {
        return vacationService.getVacationById(id);
    }

    @PostMapping
    public Vacation addVacation(@RequestBody Vacation vacation) {
        return vacationService.add(vacation);
    }

    @PutMapping("{id}")
    public Vacation replaceVacation(@PathVariable Integer id, @RequestBody Vacation vacation) {
        return vacationService.replace(id, vacation);
    }

    @DeleteMapping("{id}")
    public Vacation deleteVacation(@PathVariable Integer id) {
        return vacationService.delete(id);
    }
}
