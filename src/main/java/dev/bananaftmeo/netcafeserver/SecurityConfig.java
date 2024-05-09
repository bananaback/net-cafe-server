package dev.bananaftmeo.netcafeserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dev.bananaftmeo.netcafeserver.filters.JwtTokenFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/auth/hittest").permitAll()
                .requestMatchers("/api/auth/register").permitAll()
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/api/auth/loginwithuserinfo/**").permitAll()
                .requestMatchers("/api/auth/logout/**").permitAll()
                .requestMatchers("/api/auth/refresh").permitAll()
                .requestMatchers("/api/products/**").permitAll()
                .requestMatchers("/api/productcategories").permitAll()
                .requestMatchers("/api/orders/**").permitAll()
                .requestMatchers("/api/orderitems/**").permitAll()
                .requestMatchers("/api/rooms").permitAll()
                .requestMatchers("/api/computers/**").permitAll()
                .requestMatchers("/api/sessions/**").permitAll()
                .requestMatchers("/api/users/**").permitAll()
                .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
