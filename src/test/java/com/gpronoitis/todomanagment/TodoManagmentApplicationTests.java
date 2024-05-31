package com.gpronoitis.todomanagment;

import com.gpronoitis.todomanagment.dto.LoginDto;
import com.gpronoitis.todomanagment.dto.RegisterDto;
import com.gpronoitis.todomanagment.entity.Users;
import com.gpronoitis.todomanagment.repository.UserRepository;
import com.gpronoitis.todomanagment.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TodoManagmentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TodoManagmentApplicationTests {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    public void shouldNotRegisterAUser() throws Exception {

        mockMvc.perform(post("/api/auth/register").contentType("application/json").content("""
                            {
                            "username": "gpro",
                            "email":  "gpro@gmail,com",
                            "password": "wrong"
                            
                            }
""")
        ).andExpect(status().isBadRequest());
    }


    @Test
    public void shouldLoginAUser() throws Exception {

        mockMvc.perform(post("/api/auth/login").contentType("application/json").content("""
                            {
                            "email":  "gpro@gmail.com",
                            "password": "gpro"
                            
                            }
""")
        ).andExpect(status().isOk());

    }

    @Test
    public void shouldNotLoginUserWithWrongPassword() throws Exception {

        mockMvc.perform(post("/api/auth/login").contentType("application/json").content("""
                            {
                            "email":  "gpro@gmail.com",
                            "password": "wrong"
                            }
""")
        ).andExpect(status().isUnauthorized());

    }

}
