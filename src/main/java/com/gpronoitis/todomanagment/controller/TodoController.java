package com.gpronoitis.todomanagment.controller;

import com.gpronoitis.todomanagment.dto.TodoDto;
import com.gpronoitis.todomanagment.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todos")
public class TodoController {

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> findAllTodos() {
        List<TodoDto> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{requestedId}")
    public ResponseEntity<TodoDto> getById(@PathVariable("requestedId") Long id) {
        TodoDto todoDto = todoService.getTodo(id);
        return ResponseEntity.ok(todoDto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto todoDto) {

        TodoDto savedTodo = todoService.addTodo(todoDto);

        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{requestedId}")
    public ResponseEntity<TodoDto> updatedTodo(@RequestBody TodoDto todoDto ,@PathVariable("requestedId") Long id) {
        TodoDto updatedTodo = todoService.updateTodo(todoDto,id);

        return ResponseEntity.ok(updatedTodo);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{requrestedId}")
    public ResponseEntity<String> deleteTodo(@PathVariable("requrestedId") Long id) {
        return todoService.deleteTodo(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{requestedId}/complete")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable("requestedId") Long id) {
        TodoDto completedTodo = todoService.completeTodo(id);

        return ResponseEntity.ok(completedTodo);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{requestedId}/in-complete")
    public ResponseEntity<TodoDto> incompleteTodo(@PathVariable("requestedId") Long id) {
        TodoDto incompletedTodo = todoService.inCompleteTodo(id);

        return ResponseEntity.ok(incompletedTodo);
    }

}
