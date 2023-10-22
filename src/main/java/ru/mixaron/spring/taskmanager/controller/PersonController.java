package ru.mixaron.spring.taskmanager.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.mixaron.spring.taskmanager.dto.PersonDTO;
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

@Controller
@RequestMapping("/task")
public class PersonController {


    private final PersonService personService;

    private final CurrentUser currentUser;

    private final TasksService tasksService;

    private final Converter converter;

    public PersonController(PersonService personService, CurrentUser currentUser, TasksService tasksService, Converter converter) {
        this.personService = personService;
        this.currentUser = currentUser;
        this.tasksService = tasksService;
        this.converter = converter;
    }

    @GetMapping
    public String task(Model model, HttpSession httpSession) {
        if (currentUser.isPerson().isPresent()) {
            Person person1 = currentUser.isPerson().get();
//            List<PersonDTO> personDTOS = converter.toPersonDTO(tasksService.findByPerson(person1));
            model.addAttribute("tasks", tasksService.findByPerson(person1));
            httpSession.setAttribute("person", person1);
        }
        return "task";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("task1") Tasks task) {
        return "add";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute("task1") @Valid Tasks task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add";
        }
//        if (currentUser.isPerson().isPresent()) {
            task.setPerson(currentUser.isPerson().get());
//        }
        tasksService.add(task);
        return "redirect:/task";
    }

    @GetMapping("/{id}")
    public String watchTask(@PathVariable("id") int id, Model model, HttpSession httpSession) {
        httpSession.setAttribute("task11", tasksService.watchTask(id));
        model.addAttribute("task1", tasksService.watchTask(id));
        return "show";
    }
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, @ModelAttribute("task1") @Valid Tasks tasks, BindingResult bindingResult,
                       Model model, HttpSession httpSession) {
        Tasks tasks1 = (Tasks) httpSession.getAttribute("task11");
        if (bindingResult.hasErrors()) {
            model.addAttribute("task1", tasks1);
            return "show";
        }
        tasks1.setTask(tasks.getTask());
        tasks1.setText(tasks.getText());
        tasks1.setIsCompleted(tasks.getIsCompleted());
        tasksService.save(tasks1);
        return "redirect:/task/{id}";
    }

    @GetMapping("/sort/{name}")
    public String sort(@PathVariable("name") String sortValue, HttpSession httpSession, Model model) {
        Person person = (Person) httpSession.getAttribute("person");
        model.addAttribute("tasks", tasksService.findAllSort(sortValue, person));
        return "task";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") UUID id) {
        tasksService.delete(id);
        return "redirect:/task";
    }
}
