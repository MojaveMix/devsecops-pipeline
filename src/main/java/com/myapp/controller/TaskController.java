package com.myapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.dto.TaskDTO;
import com.myapp.model.Task;
import com.myapp.model.User;
import com.myapp.repository.TaskRepository;
import com.myapp.repository.UserRepository;
import com.myapp.service.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    

    private final TaskService taskService;
    private  final TaskRepository taskRepository;
    private  final UserRepository userRepository;




    
    @GetMapping("/get")
    public String getTasks() {
        return "Hello World";
    }

    @GetMapping("/all")
    public List<Task> AllTasks() {
        return taskService.getAllTasks();
    }

@PostMapping("/create")
public ResponseEntity<Task> createTask(@RequestBody TaskDTO dto) {
    // 1. Fetch user by id
    User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

    // 2. Map DTO to Task
    Task task = new Task();
    task.setName(dto.getName());
    task.setDescription(dto.getDescription());
    task.setDate(dto.getDate());
    task.setUser(user); // assign User object, NOT UUID

    // 3. Save Task
    Task savedTask = taskRepository.save(task);

    return ResponseEntity.ok(savedTask);
}
}
