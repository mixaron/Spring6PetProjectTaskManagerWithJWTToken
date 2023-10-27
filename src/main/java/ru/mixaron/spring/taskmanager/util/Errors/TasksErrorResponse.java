package ru.mixaron.spring.taskmanager.util.Errors;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TasksErrorResponse {
    private String message;

    private long timestamp;



}
