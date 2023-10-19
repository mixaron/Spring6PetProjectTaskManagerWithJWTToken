package ru.mixaron.spring.taskmanager.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mixaron.spring.taskmanager.models.Person;
import ru.mixaron.spring.taskmanager.repository.PersonRepo;

import java.util.Optional;

@Service
public class PersonService {

    private final PasswordEncoder passwordEncoder;
    private final PersonRepo personRepo;

    public PersonService(PasswordEncoder passwordEncoder, PersonRepo personRepo) {
        this.passwordEncoder = passwordEncoder;
        this.personRepo = personRepo;
    }

    public void save(Person person) {
        String encode = passwordEncoder.encode(person.getPassword());
        person.setRole("ROLE_USER");
        person.setPassword(encode);
        personRepo.save(person);
    }

    public Optional<Person> SearchByName(String name) {
        return personRepo.findByName(name);
    }
}
