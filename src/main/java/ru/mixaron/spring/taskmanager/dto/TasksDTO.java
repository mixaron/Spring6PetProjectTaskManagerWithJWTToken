package ru.mixaron.spring.taskmanager.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import ru.mixaron.spring.taskmanager.models.Person;

import java.util.Date;

@Getter
@Setter
public class TasksDTO {
    @NotEmpty(message = "Name of the task cannot be empty")
    private String task;

    private String text;

    private String isCompleted;

    private Date dateOfCreation;

    private Date dateOfChange;

    private Person person;
}
