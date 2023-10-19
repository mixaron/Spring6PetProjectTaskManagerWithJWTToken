package ru.mixaron.spring.taskmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mixaron.spring.taskmanager.models.Person;
import ru.mixaron.spring.taskmanager.service.PersonService;

@Controller
@RequestMapping("/task")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping("/auth")
    public String auth() {
        return "/auth/auth";
    }
    @GetMapping("/registration")
    public String reg(@ModelAttribute("person") Person person) {
        return "/auth/reg";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("person")Person person) {
        personService.save(person)  ;
        return "redirect:/task/auth";
    }
}
