package com.gpronoitis.todomanagment.controller;

import com.gpronoitis.todomanagment.dto.JwtResponseDto;
import com.gpronoitis.todomanagment.dto.LoginDto;
import com.gpronoitis.todomanagment.dto.RegisterDto;
import com.gpronoitis.todomanagment.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto) {
        String response =  authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> loginUser(@RequestBody LoginDto loginDto) {
        String response = authService.login(loginDto);

        JwtResponseDto responseDto = new JwtResponseDto();
        responseDto.setAccessToken(response);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }


}
