package ru.mixaron.spring.taskmanager.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.mixaron.spring.taskmanager.dto.TasksDTO;
import ru.mixaron.spring.taskmanager.models.Person;
import ru.mixaron.spring.taskmanager.models.Tasks;
import ru.mixaron.spring.taskmanager.security.CurrentUser;
import ru.mixaron.spring.taskmanager.service.PersonService;
import ru.mixaron.spring.taskmanager.service.TasksService;
import ru.mixaron.spring.taskmanager.util.Converter;
import ru.mixaron.spring.taskmanager.util.Errors.SortErrorResponse;
import ru.mixaron.spring.taskmanager.util.Errors.TasksNotFoundException;
import ru.mixaron.spring.taskmanager.util.Errors.TasksErrorResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
@ControllerAdvice
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
            return new ResponseEntity<>("Something bad", HttpStatus.BAD_REQUEST);
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

    @ExceptionHandler
    private ResponseEntity<TasksErrorResponse> handlerException(TasksNotFoundException e) {
        TasksErrorResponse response = new TasksErrorResponse(
                "Task with this id not found", System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<TasksErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        TasksErrorResponse response = new TasksErrorResponse("Invalid UUID format", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
            Person person1 = currentUser.isPerson().get();
            List<Tasks> tasks = tasksService.findAllSort(sortValue, person1);
            return ResponseEntity.ok(tasksService.returnALl(tasks));
    }

    @ExceptionHandler
    public ResponseEntity<TasksErrorResponse> sortException(SortErrorResponse e) {
        TasksErrorResponse response = new TasksErrorResponse(
                "invalid request ",
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) {
        tasksService.delete(id);
        return ResponseEntity.ok("Delete");
    }
}
