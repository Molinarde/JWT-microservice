package com.travel.authservice.controller;

import com.travel.authservice.model.User;
import com.travel.authservice.model.dto.AuthRequest;
import com.travel.authservice.model.dto.AuthResponse;
import com.travel.authservice.model.dto.RegRequest;
import com.travel.authservice.service.AuthService;
import com.travel.authservice.service.UserService;
import com.travel.authservice.service.Util.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest authRequest){

        User user = userService.findByEmailAndPassword(authRequest.getEmail(), authRequest.getPassword());

        if (user != null){
            String token = jwtUtil.generateToken(user);
            AuthResponse authResponse = new AuthResponse();
            authResponse.setAccessToken(token);
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping("/signup")
    public ResponseEntity registerUser(@RequestBody RegRequest regRequest){
        User user = new User();
        user.setUsername(regRequest.getUsername());
        user.setPassword(BCrypt.hashpw(regRequest.getPassword(), BCrypt.gensalt()));
        user.setEmail(regRequest.getEmail());
        user.setCountry(regRequest.getCountry());

        boolean addUser = userService.addUser(user);

        if (addUser)
            return new ResponseEntity(HttpStatus.CREATED);

        return new ResponseEntity(HttpStatus.CONFLICT);
    }
}
