package ru.mixaron.spring.taskmanager.service;


import org.springframework.scheduling.config.Task;
import org.springframework.transaction.annotation.Transactional;
import ru.mixaron.spring.taskmanager.models.Tasks;
import org.springframework.stereotype.Service;
import ru.mixaron.spring.taskmanager.models.Person;
import ru.mixaron.spring.taskmanager.repository.TasksRepo;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TasksService {

    private final TasksRepo tasksRepo;

    public TasksService(TasksRepo tasksRepo) {
        this.tasksRepo = tasksRepo;
    }

    public List<Tasks> findByPerson(Person person) {
        return tasksRepo.findByPerson(person);
    }

    public void add(Tasks tasks) {
        tasks.setIsCompleted("Не приступил");
        tasks.setDateOfCreation(new Date());
        tasksRepo.save(tasks);
    }
    public void save(Tasks tasks) {
        tasks.setDateOfChange(new Date());
        if (tasks.getIsCompleted() == null) {
            tasks.setIsCompleted("Выполнил");
        }
        tasksRepo.save(tasks);
    }

    public Tasks watchTask(int id) {
        return tasksRepo.findById(id).orElse(null);
    }

    public List<Tasks> findAllSort(String sort, Person person) {
        if (sort.equals("create")) {
            return tasksRepo.findAllByPersonOrderByDateOfCreation(person);
        }
        if (sort.equals("change")) {
            return tasksRepo.findAllByPersonOrderByDateOfChange(person);
        }
        if (sort.equals("status")) {
            return tasksRepo.findAllByPersonOrderByIsCompleted(person);
        }
        if (sort.equals("name")) {
            return tasksRepo.findAllByPersonOrderByTask(person);
        }
        return null;
    }

    public void delete(UUID id) {
        tasksRepo.deleteById(id);
    }

}
