package ru.mixaron.spring.taskmanager.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.mixaron.spring.taskmanager.util.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "password cannor be empty")
    @Size(min = 3, max = 15, message = "Password must be on 3 between 15")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
