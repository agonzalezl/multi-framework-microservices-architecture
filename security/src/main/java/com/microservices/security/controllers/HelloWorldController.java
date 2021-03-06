package com.microservices.security.controllers;

import com.microservices.security.MyUserDetailsService;
import com.microservices.security.models.AuthenticationRequest;
import com.microservices.security.models.AuthenticationResponse;
import com.microservices.security.repositories.UserRepository;
import com.microservices.security.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
class HelloWorldController {

	@Autowired
	UserRepository repository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

	@RequestMapping(value =  "/test", method = RequestMethod.GET )
	public String firstPage() {
		return "Hello World! "+repository.findAll().size();
	}

	@RequestMapping(value = "/validate" , method = RequestMethod.GET)
	public ResponseEntity<?> validateToken(@RequestHeader("token") String token) {
		// ResponseEntity<?>

		try{
			String username = jwtUtil.extractUsername(token);
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if(jwtUtil.validateToken(token, userDetails)){
				return ResponseEntity.ok().build();
			}
		}catch(Exception e){
			// Expired token
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		//return "Hello world";
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}


		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}