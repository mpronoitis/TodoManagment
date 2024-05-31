package com.gpronoitis.todomanagment.service.impl;

import com.gpronoitis.todomanagment.dto.LoginDto;
import com.gpronoitis.todomanagment.dto.RegisterDto;
import com.gpronoitis.todomanagment.entity.Role;
import com.gpronoitis.todomanagment.entity.Users;
import com.gpronoitis.todomanagment.exception.TodoAPIException;
import com.gpronoitis.todomanagment.repository.RoleRepository;
import com.gpronoitis.todomanagment.repository.UserRepository;
import com.gpronoitis.todomanagment.security.JwtService;
import com.gpronoitis.todomanagment.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, ModelMapper modelMapper, AuthenticationManager authenticationManager, JwtService jwtService,UserRepository userRepository,RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public String register(RegisterDto registerDto) {
        // Username and email must be unique
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        //save User to db
        Users user = new Users();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        //By default give a ROLE_USER role to the user
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setName("ROLE_USER");
        roles.add(role);

        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successful";

    }

    @Override
    public String login(LoginDto loginDto) {
     //check if users exists with these credentials
        Authentication authentication = null;

             authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(),
                    loginDto.getPassword()
            ));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtService.GenerateToken(authentication.getName());
            return token;


    }
}
