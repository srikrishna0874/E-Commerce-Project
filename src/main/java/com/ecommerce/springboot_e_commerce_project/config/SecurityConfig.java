package com.ecommerce.springboot_e_commerce_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/categories/**", "/products/**").permitAll()
                                .requestMatchers("/cart/*", "/orders/place/**").hasRole("USER")
                                .requestMatchers(HttpMethod.GET, "/orders/**").hasAnyRole("USER","ADMIN")
                                .requestMatchers(HttpMethod.POST, "/categories/**", "/products/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/categories/**", "/products/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/categories/**", "/products/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/orders/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/orders/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return httpSecurity.build();
    }

}
