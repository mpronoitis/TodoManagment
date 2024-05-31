package com.gpronoitis.todomanagment.repository;

import com.gpronoitis.todomanagment.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo,Long> {
}
