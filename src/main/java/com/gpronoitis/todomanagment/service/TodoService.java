package com.gpronoitis.todomanagment.service;

import com.gpronoitis.todomanagment.dto.TodoDto;
//import com.gpronoitis.todomanagment.entity.Todo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TodoService {

    TodoDto addTodo(TodoDto todoDto);

    TodoDto getTodo(Long id);

    List<TodoDto> getAllTodos();

    TodoDto updateTodo(TodoDto todoDto, Long id);

    ResponseEntity<String> deleteTodo(Long id);

    TodoDto completeTodo(Long id);

    TodoDto inCompleteTodo(Long id);
}
