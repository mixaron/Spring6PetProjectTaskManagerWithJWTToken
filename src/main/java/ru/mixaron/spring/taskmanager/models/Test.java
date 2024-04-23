package ru.mixaron.spring.taskmanager.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "test")
@Entity
@Getter
@Setter
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    String name;
}
