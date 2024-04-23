package ru.mixaron.spring.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mixaron.spring.taskmanager.models.Test;
@Repository
public interface TestRepo extends JpaRepository<Test, Integer> {
}
