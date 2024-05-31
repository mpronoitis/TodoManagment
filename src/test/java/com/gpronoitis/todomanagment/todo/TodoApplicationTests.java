package com.gpronoitis.todomanagment.todo;

import com.gpronoitis.todomanagment.TodoManagmentApplication;
import com.gpronoitis.todomanagment.dto.TodoDto;
import com.gpronoitis.todomanagment.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TodoManagmentApplication.class)
@AutoConfigureMockMvc
public class TodoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;


    @Test
    public void shouldReturnTodosWithAuthentication() throws Exception {
        // use mockmvc to test the controller
        String token = jwtService.GenerateToken("gpro");
        mockMvc.perform(get("/api/todos").with(request -> {
            request.addHeader("Authorization", "Bearer " + token);
            return request;
        })).andExpect(status().isOk());

    }

    @Test
    public void shouldReturnUnAuthorizedWithoutAuthentication() throws Exception {
        // use mockmvc to test the controller
        mockMvc.perform(get("/api/todos")).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnForbiddenWithInvalidRole() throws Exception {
        // use mockmvc to test the controller
        String token = jwtService.GenerateToken("gpro");
        mockMvc.perform(delete("/api/todos/1").with(request -> {
            request.addHeader("Authorization", "Bearer " + token);
            return request;
        })).andExpect(status().isForbidden());
    }

    @Test
    public void shouldDeleteTodoWithAppropriateRole() throws Exception {
        // use mockmvc to test the controller
        String token = jwtService.GenerateToken("admin");
        mockMvc.perform(delete("/api/todos/1").with(request -> {
            request.addHeader("Authorization", "Bearer " + token);
            return request;
        })).andExpect(status().isOk());


        mockMvc.perform(get("/api/todos").with(request -> {
            request.addHeader("Authorization", "Bearer " + token);
            return request;
        })).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1)).andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Spring Boot"));
    }

    @Test
    public void shouldReturnForbiddenWithInvalidToken() throws Exception {
        // use mockmvc to test the controller
        mockMvc.perform(get("/api/todos").with(request -> {
            request.addHeader("Authorization", "Bearer " + "invalidtoken");
            return request;
        })).andExpect(status().isForbidden());
    }

    @Test
    public void shouldUpdateTodoWithAdminRole() throws Exception {
        // use mockmvc to test the controller
        TodoDto todoDto = new TodoDto();
        todoDto.setDescription("New Description");
        todoDto.setCompleted(true);
        todoDto.setTitle("New Title");
        String token = jwtService.GenerateToken("admin");

        mockMvc.perform(put("/api/todos/1").contentType("application/json").content("""
                      {
                      "title": "New Title",
                      "description": "New Description",
                      "isCompleted": true
                      
                      }
""").with(request -> {
        request.addHeader("Authorization", "Bearer " + token); return request;
    })).andExpect(content().contentType("application/json")).andExpect(status().isOk());

        mockMvc.perform(get("/api/todos/1").with(request -> {
            request.addHeader("Authorization", "Bearer " + token);
            return request;
        })).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.title").value("New Title"));
    }

    @Test
    void shouldReturnUnAuthorizedIfTokenHasExpires() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcxNzA3Mzk3MSwiZXhwIjoxNzE3MDc0MDMxfQ.vPCGHupf5_cWbkRDtSnpcqjBPU4YTyLFta5xitZyWW8";

        mockMvc.perform(get("/api/todos").with(request -> {
            request.addHeader("Authorization", "Bearer " + token);
            return request;
        })).andExpect(status().isUnauthorized());
    }


}
