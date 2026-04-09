package com.myapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myapp.model.Task;
import com.myapp.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

       public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }
}