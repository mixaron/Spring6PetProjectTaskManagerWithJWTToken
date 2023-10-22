package ru.mixaron.spring.taskmanager.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.mixaron.spring.taskmanager.models.Person;
import ru.mixaron.spring.taskmanager.service.PersonService;

import java.util.Optional;

@Component
public class CurrentUser {

    private final PersonService personService;

    public CurrentUser(PersonService personService) {
        this.personService = personService;
    }

    public UserDetails getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return (UserDetails) principal;
        }
        return null;
    }

    public String getCurrentUsername() {
        UserDetails userDetails = getCurrentUser();
        return (userDetails != null) ? userDetails.getUsername() : null;
    }

    public Optional<Person> isPerson() {
        String username = getCurrentUsername();
            return personService.SearchByName(username);
    }
}