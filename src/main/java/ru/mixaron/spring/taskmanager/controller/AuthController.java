package ru.mixaron.spring.taskmanager.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mixaron.spring.taskmanager.dto.PersonDTO;
import ru.mixaron.spring.taskmanager.models.Person;
import ru.mixaron.spring.taskmanager.service.PersonService;
import ru.mixaron.spring.taskmanager.util.Converter;

@Controller
public class AuthController {

    private final PersonService personService;

    private final Converter converter;

    public AuthController(PersonService personService, Converter converter) {
        this.personService = personService;
        this.converter = converter;
    }

    @GetMapping("/auth")
    public String auth(@RequestParam(value = "error", required = false) String error, Model model, @ModelAttribute("person") PersonDTO person) {
        if  (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password");
        }
        return "/auth/auth";
    }
    @GetMapping("/registration")
    public String reg(@ModelAttribute("person") PersonDTO personDTO) {
        return "/auth/reg";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("person") @Valid PersonDTO personDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "auth/reg";
        }
        Person person = converter.toPerson(personDTO);
        personService.save(person);
        return "redirect:/task/auth";
    }

}
