package com.blog.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.security.CustomUserDetailsService;
import com.blog.security.JwtAuthenticationEntryPoint;
import com.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableWebMvc
public class CustomConfig {

	public static final String[] PUBLIC_URLS= {
			"/api/auth/**",
			"/v3/api-docs/**",
			"/v2/api-docs/**",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**",
			"/swagger.json",
			"/swagger-ui.html",
	};
	
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
		security.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers(PUBLIC_URLS).permitAll()
						.anyRequest().authenticated())
				.exceptionHandling(ex -> ex.authenticationEntryPoint(this.authenticationEntryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return security.build();
	}

	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(customUserDetailsService)
				.passwordEncoder(encoder()).and().build();
	}

	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
