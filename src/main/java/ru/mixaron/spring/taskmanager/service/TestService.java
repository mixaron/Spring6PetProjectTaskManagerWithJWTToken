package ru.mixaron.spring.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mixaron.spring.taskmanager.models.Test;
import ru.mixaron.spring.taskmanager.repository.TestRepo;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepo testRepo;
    public void testSend(Test test) {
        testRepo.save(test);
    }
}
