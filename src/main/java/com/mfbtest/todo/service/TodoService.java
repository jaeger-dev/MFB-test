package com.mfbtest.todo.service;

import com.mfbtest.todo.dto.CreateTodoRequest;
import com.mfbtest.todo.dto.TodoResponse;
import com.mfbtest.todo.dto.UpdateTodoRequest;
import com.mfbtest.todo.entity.Todo;
import com.mfbtest.todo.exception.TodoNotFoundException;
import com.mfbtest.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;

    public List<TodoResponse> findAll() {
        return todoRepository.findAll().stream()
                .map(TodoResponse::from)
                .toList();
    }

    public TodoResponse findById(Long id) {
        return TodoResponse.from(getOrThrow(id));
    }

    @Transactional
    public TodoResponse create(CreateTodoRequest request) {
        Todo todo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .build();
        return TodoResponse.from(todoRepository.save(todo));
    }

    @Transactional
    public TodoResponse update(Long id, UpdateTodoRequest request) {
        Todo todo = getOrThrow(id);
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        return TodoResponse.from(todoRepository.save(todo));
    }

    @Transactional
    public TodoResponse toggleStatus(Long id) {
        Todo todo = getOrThrow(id);
        todo.setCompleted(!todo.isCompleted());
        return TodoResponse.from(todoRepository.save(todo));
    }

    @Transactional
    public void delete(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new TodoNotFoundException(id);
        }
        todoRepository.deleteById(id);
    }

    private Todo getOrThrow(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
    }
}
