package ru.mixaron.spring.taskmanager.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import ru.mixaron.spring.taskmanager.dto.PersonDTO;
import ru.mixaron.spring.taskmanager.dto.TasksDTO;
import ru.mixaron.spring.taskmanager.models.Person;
import ru.mixaron.spring.taskmanager.models.Tasks;

@Service
public class Converter {

    private final ModelMapper modelMapper;



    public Converter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Person toPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    public PersonDTO toPersonDTO(Person person) {
        return  modelMapper.map(person, PersonDTO.class);
    }

    public Tasks toTasks(TasksDTO tasksDTO) {
        return modelMapper.map(tasksDTO, Tasks.class);
    }

    public TasksDTO toTaskDTO(Tasks tasks) {
        return modelMapper.map(tasks, TasksDTO.class);
    }
}
