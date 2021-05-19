package com.example.demo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RestController
@RequestMapping("/api/auth")
public class UserAuthController {

	@Autowired
	UserService userService;
	
	
	@PostMapping
	public ResponseEntity<?> addUser(@RequestBody User user){
		User addeduser = userService.addUser(user);
		return new ResponseEntity<>(addeduser,HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user){
		
		String jwtToken = "";
		Map<String,String> map = new HashMap<>();
		
		try {
			jwtToken = getToken(user.getUsername(),user.getPassword());
			map.clear();
			map.put("message", "User logged in successfully");
			map.put("token", jwtToken);
		}catch(Exception e) {
			map.clear();
			map.put("message", e.getMessage());
			map.put("token", null);
		}
		
		return new ResponseEntity<>(map,HttpStatus.OK);
		
		
	}
	
	public String getToken(String username, String password) throws ServletException {
		String jwtToken = "";
		
		if(username == null || password == null) {
			throw new ServletException("Please fill the username and password correctly");
		}
		
		boolean status = userService.validate(username,password);
		
		if(!status) {
			throw new ServletException("Invalid Credentials");
		}
		
		// generate the token
		
		jwtToken = Jwts
					.builder()
						.setSubject(username)
						.setIssuedAt(new Date())
						.setExpiration(new Date(System.currentTimeMillis()+300000))
						.signWith(SignatureAlgorithm.HS256, "hclwave1")
						.compact();
		
		
		return jwtToken;
		
	}
	

	
}
