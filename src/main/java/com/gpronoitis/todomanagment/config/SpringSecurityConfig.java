package com.gpronoitis.todomanagment.config;

import com.gpronoitis.todomanagment.security.JwtAutenticationFilter;
import com.gpronoitis.todomanagment.security.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SpringSecurityConfig {


    @Autowired
    private JwtAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private JwtAutenticationFilter autenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable()).authorizeHttpRequests(
                (authorize) -> {
//                    authorize.requestMatchers(HttpMethod.POST,"/api/**").hasAnyRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.PUT,"/api/**").hasAnyRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.DELETE,"/api/**").hasAnyRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.GET,"/api/**").permitAll();
                    authorize.requestMatchers("api/auth/**").permitAll();
                    authorize.anyRequest().authenticated();

                }).httpBasic(Customizer.withDefaults()).exceptionHandling((exception) -> exception.authenticationEntryPoint(customAuthenticationEntryPoint));


        http.addFilterBefore(autenticationFilter, UsernamePasswordAuthenticationFilter.class);
        //http.exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//
//        UserDetails user = User.withUsername("user").password(passwordEncoder.encode("user")).roles("USER").build();
//        UserDetails admin = User.withUsername("admin").password(passwordEncoder.encode("admin")).roles("USER", "ADMIN").build();
//        UserDetails superadmin = User.withUsername("superadmin").password(passwordEncoder.encode("superadmin")).roles("USER", "ADMIN", "SUPERADMIN").build();
//
//       return new InMemoryUserDetailsManager(user, admin, superadmin);
//    }
//
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
