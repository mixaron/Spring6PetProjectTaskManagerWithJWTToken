package ru.mixaron.spring.taskmanager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.Date;
import java.util.UUID;

@Table(name = "tasks")
@Entity
@Getter
@Setter
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "task")
    @NotEmpty(message = "Name of the task cannot be empty")
    private String task;

    @Column(name = "tasktext")
    private String text;

    @Column(name = "iscompleted")
    private String isCompleted;

    @Column(name = "date_of_creation", updatable = false)
    private Date dateOfCreation;

    @Column(name = "date_of_change")
    private Date dateOfChange;

    @ManyToOne
    @JoinColumn(name = "person", referencedColumnName = "id")
    Person person;
}
