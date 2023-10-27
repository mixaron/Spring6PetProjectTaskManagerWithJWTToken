package ru.mixaron.spring.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mixaron.spring.taskmanager.models.Person;
import ru.mixaron.spring.taskmanager.models.Tasks;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TasksRepo extends JpaRepository<Tasks, Integer> {
    List<Tasks> findByPerson(Person person);

    List<Tasks> findAllByPersonOrderByTask(Person person);

    List<Tasks> findAllByPersonOrderByDateOfChange(Person person);

    List<Tasks> findAllByPersonOrderByDateOfCreation(Person person);

    List<Tasks> findAllByPersonOrderByIsCompleted(Person person);

    Optional<List<Tasks>> findAllByPersonOrderByText(Person person);

    void deleteById(UUID uuid);

    Optional<Tasks> findById(UUID id);
}
