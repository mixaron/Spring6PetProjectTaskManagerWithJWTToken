package ru.mixaron.spring.taskmanager.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import ru.mixaron.spring.taskmanager.dto.TasksDTO;
import ru.mixaron.spring.taskmanager.models.Person;
import ru.mixaron.spring.taskmanager.models.Tasks;
import ru.mixaron.spring.taskmanager.security.CurrentUser;
import ru.mixaron.spring.taskmanager.service.PersonService;
import ru.mixaron.spring.taskmanager.service.TasksService;
import ru.mixaron.spring.taskmanager.util.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
public class PersonController {


    private final PersonService personService;



    private final TasksService tasksService;

    private final Converter converter;

    private final CurrentUser currentUser;



    @GetMapping
    public ResponseEntity<String> task() {
        if (currentUser.isPerson().isPresent()) {
            Person person1 = currentUser.isPerson().get();
            List<Tasks> person = tasksService.findByPerson(person1);
            return ResponseEntity.ok(tasksService.returnALl(person));

        }
        return ResponseEntity.ok(null);
    }

//    @GetMapping
//    public List<Tasks> getAllTasks() {
//        Person person1 = currentUser.isPerson().get();
//        return tasksService.findByPerson(person1);
//    }
    // вызывает Cannot call sendError(). Ушел кушать.

//    @GetMapping("/add")
//    public String add(@RequestBody Tasks task) {
//        return "add";
//    }

    @PostMapping("/add")
    public ResponseEntity<String> addPost(@RequestBody @Valid TasksDTO task1, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok("Not Created");
        }
        Tasks task =  converter.toTasks(task1);

            task.setPerson(currentUser.isPerson().get());

        tasksService.add(task);
        return ResponseEntity.ok("Add");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> watchTask(@PathVariable("id") UUID id) {
        return ResponseEntity.ok("Получилось\n" + tasksService.returnOne(tasksService.watchTask(id)));
    }
//    @PostMapping("/{id}/edit")
//    public ResponseEntity<Tasks> edit(@PathVariable("id") int id, @RequestBody @Valid Tasks tasks, BindingResult bindingResult, HttpSession httpSession) {
//        Tasks tasks1 = (Tasks) httpSession.getAttribute("task11");
//        if (bindingResult.hasErrors()) {
//            return ResponseEntity.ok(null);
//        }
//        tasks1.setTask(tasks.getTask());
//        tasks1.setText(tasks.getText());
//        tasks1.setIsCompleted(tasks.getIsCompleted());
//        tasksService.save(tasks1);
//        return ResponseEntity.ok(tasksService.watchTask(id));
//    }

    @GetMapping("/sort/{name}")
    public ResponseEntity<String> sort(@PathVariable("name") String sortValue) {
        if (currentUser.isPerson().isPresent()) {
            Person person1 = currentUser.isPerson().get();
            List<Tasks> tasks = tasksService.findAllSort(sortValue, person1);
            return ResponseEntity.ok(tasksService.returnALl(tasks));
        }
        return ResponseEntity.ok("Not ok");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) {
        tasksService.delete(id);
        return ResponseEntity.ok("Delete");
    }
}
