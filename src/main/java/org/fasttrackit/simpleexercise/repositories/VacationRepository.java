package org.fasttrackit.simpleexercise.repositories;

import org.fasttrackit.simpleexercise.domain.Vacation;
import org.springframework.data.repository.CrudRepository;

public interface VacationRepository extends CrudRepository<Vacation, Integer> {
}
