package com.mfbtest.todo.controller;

import com.mfbtest.todo.dto.CreateTodoRequest;
import com.mfbtest.todo.dto.TodoResponse;
import com.mfbtest.todo.dto.UpdateTodoRequest;
import com.mfbtest.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public List<TodoResponse> getAll() {
        return todoService.findAll();
    }

    @GetMapping("/{id}")
    public TodoResponse getById(@PathVariable Long id) {
        return todoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoResponse create(@Valid @RequestBody CreateTodoRequest request) {
        return todoService.create(request);
    }

    @PutMapping("/{id}")
    public TodoResponse update(@PathVariable Long id, @Valid @RequestBody UpdateTodoRequest request) {
        return todoService.update(id, request);
    }

    @PatchMapping("/{id}/status")
    public TodoResponse toggleStatus(@PathVariable Long id) {
        return todoService.toggleStatus(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        todoService.delete(id);
    }
}
