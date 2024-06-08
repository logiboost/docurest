package org.docurest.security;

import org.docurest.security.tools.Endpoint;
import org.docurest.security.tools.EndpointScanner;
import org.docurest.Infra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Set;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public JwtTokenFilter jwtTokenFilter(JwtUtil jwtUtil, Infra infra) {
        return new JwtTokenFilter(jwtUtil, infra);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtTokenFilter jwtTokenFilter) throws Exception {

        final Stream<String> swaggerEndpoints = Stream.of(
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/webjars/**"
        );

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> {
                            Set<Endpoint> endpoints = EndpointScanner.scanContext(applicationContext);
                            for (Endpoint endpoint : endpoints) {
                                if (endpoint.isPermitAll()) {
                                    System.out.println("permitAll on " + endpoint.getMethod().name() + endpoint.getPath());
                                    authorizeRequests.requestMatchers(endpoint.getMethod().name(), endpoint.getPath()).permitAll();
                                }
                            }

                            authorizeRequests
                                .requestMatchers(swaggerEndpoints.toArray(String[]::new)).permitAll()
                                .anyRequest().authenticated();
                        }
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

}
