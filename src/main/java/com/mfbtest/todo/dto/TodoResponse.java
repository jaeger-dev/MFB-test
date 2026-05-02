package com.mfbtest.todo.dto;

import com.mfbtest.todo.entity.Todo;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TodoResponse {

    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdDate;

    public static TodoResponse from(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .completed(todo.isCompleted())
                .createdDate(todo.getCreatedDate())
                .build();
    }
}
