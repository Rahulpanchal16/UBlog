package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.JwtAuthRequest;
import com.blog.payload.JwtAuthResponse;
import com.blog.payload.UserDto;
import com.blog.security.JwtTokenHelper;
import com.blog.services.UserService;

@RestController
@RequestMapping(path = "/api/")
public class AuthController {

	@Autowired
	private JwtTokenHelper tokenHelper;

	@Autowired
	private UserDetailsService detailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@PostMapping(path = "/auth/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest authRequest) throws Exception {
		this.authenticate(authRequest.getUsername(), authRequest.getPassword());
		UserDetails userDetails = this.detailsService.loadUserByUsername(authRequest.getUsername());
		String token = this.tokenHelper.generateToken(userDetails);
		JwtAuthResponse authResponse = new JwtAuthResponse();
		authResponse.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(authResponse, HttpStatus.OK);
	}

	private void authenticate(String email, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
				password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("Bad Credentials");
			throw new Exception("Invalid Credentials");
		}
	}

	// registering a new user
	@PostMapping(path = "/auth/register_new_user")
	public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto) {
		UserDto registeredNewUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registeredNewUser, HttpStatus.CREATED);
	}

}
