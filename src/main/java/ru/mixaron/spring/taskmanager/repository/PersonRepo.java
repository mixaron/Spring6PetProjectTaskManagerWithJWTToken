package ru.mixaron.spring.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mixaron.spring.taskmanager.models.Person;
import ru.mixaron.spring.taskmanager.models.Tasks;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepo extends JpaRepository<Person, Integer> {
    Optional<Person> findByName(String name);

}
