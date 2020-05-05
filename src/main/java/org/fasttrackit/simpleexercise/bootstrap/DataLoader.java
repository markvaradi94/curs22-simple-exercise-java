package org.fasttrackit.simpleexercise.bootstrap;

import org.fasttrackit.simpleexercise.domain.Vacation;
import org.fasttrackit.simpleexercise.domain.VacationReader;
import org.fasttrackit.simpleexercise.repositories.VacationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final VacationRepository vacationRepository;

    public DataLoader(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        long count = vacationRepository.count();
        if (count == 0) loadData();
    }

    private void loadData() {
        String fileLocation = "vacations.txt";
        VacationReader reader = new VacationReader(fileLocation);
        List<Vacation> vacations = reader.read();
        vacations.forEach(vacationRepository::save);
        System.out.println("Loaded vacations ...");
    }
}
