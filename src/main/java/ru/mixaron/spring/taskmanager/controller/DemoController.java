package ru.mixaron.spring.taskmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mixaron.spring.taskmanager.models.Test;
import ru.mixaron.spring.taskmanager.service.TestService;


@RestController
@RequestMapping("api/v1/auth/demo")
@RequiredArgsConstructor
public class DemoController {

    private final TestService testService;

    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from auth token");
    }

    @PostMapping("/test")
    public ResponseEntity<HttpStatus> test(Test tasks) {
        testService.testSend(tasks);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
