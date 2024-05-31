package com.gpronoitis.todomanagment.service.impl;

import com.gpronoitis.todomanagment.dto.TodoDto;
import com.gpronoitis.todomanagment.entity.Todo;
import com.gpronoitis.todomanagment.exception.ResourceNotFound;
import com.gpronoitis.todomanagment.repository.TodoRepository;
import com.gpronoitis.todomanagment.service.TodoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;
    private ModelMapper modelMapper;

    public TodoServiceImpl(TodoRepository todoRepository, ModelMapper modelMapper) {
        this.todoRepository = todoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TodoDto addTodo(TodoDto todoDto) {

        //convert dto object to todo object
        //save todo object to db
        Todo todo = modelMapper.map(todoDto, Todo.class);

        //Table JPA Entity
        Todo savedTodo =  todoRepository.save(todo);

        //Converted savedtodo to dto object
        TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);

        return savedTodoDto;
    }

    @Override
    public TodoDto getTodo(Long id) {

       Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Todo not found with id:" + id));

       return modelMapper.map(todo, TodoDto.class);

    }

    @Override
    public List<TodoDto> getAllTodos() {
        return todoRepository.findAll().stream().map((todo) -> modelMapper.map(todo, TodoDto.class)).collect(Collectors.toList());
    }

    @Override
    public TodoDto updateTodo(TodoDto todoDto, Long id) {
        Todo findedTodo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Todo not found with id:" + id));

        findedTodo.setCompleted(todoDto.isCompleted());
        findedTodo.setTitle(todoDto.getTitle());
        findedTodo.setDescription(todoDto.getDescription());

        Todo updatedTodo = todoRepository.save(findedTodo);

        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public ResponseEntity<String> deleteTodo(Long id) {

        Todo findedTodo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Todo not found with id:" + id));

        todoRepository.deleteById(id);

        return ResponseEntity.ok("Todo deleted successfully");

    }

    @Override
    public TodoDto completeTodo(Long id) {
       Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Todo not found with id " +id));
       todo.setCompleted(Boolean.TRUE);

       Todo updatedTodo = todoRepository.save(todo);

       return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(Long id) {

        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Todo not found with id " +id));

        todo.setCompleted(Boolean.FALSE);
        Todo updatedTodo = todoRepository.save(todo);

        return modelMapper.map(updatedTodo, TodoDto.class);
    }
}
