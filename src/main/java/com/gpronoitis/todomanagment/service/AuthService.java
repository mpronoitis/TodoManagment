package com.gpronoitis.todomanagment.service;

import com.gpronoitis.todomanagment.dto.LoginDto;
import com.gpronoitis.todomanagment.dto.RegisterDto;

public interface AuthService {

    String register(RegisterDto registerDto);

    String login(LoginDto loginDto);
}
