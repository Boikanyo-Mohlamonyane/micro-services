package com.crud.crud_operation.config;

import com.crud.crud_operation.security.Jfilters;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private Jfilters jfilters;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        http
                .csrf(csrf->csrf.disable()
                .authorizeHttpRequests(
                        auth->auth
                                .requestMatchers("/api/auth/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui.html")
                                .permitAll()
                                .requestMatchers("/api/admin/**")
                                .hasRole("ADMIN")
                                .requestMatchers("/api/users/**")
                                .hasAnyRole("USER","ADMIN")
                                .anyRequest().authenticated()
                ).sessionManagement(

                        session->
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )
                );

        http.addFilterBefore(jfilters, UsernamePasswordAuthenticationFilter.class);



   return http.build();
    }
}
