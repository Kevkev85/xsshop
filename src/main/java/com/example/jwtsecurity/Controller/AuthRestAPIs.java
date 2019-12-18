package com.example.jwtsecurity.Controller;

import com.example.jwtsecurity.Message.OtherJwtResponse;
import com.example.jwtsecurity.Message.ResponseMessage;
import com.example.jwtsecurity.Model.User;
import com.example.jwtsecurity.Repository.UserRepository;
import com.example.jwtsecurity.Security.JwtProvider;
import com.example.jwtsecurity.Views.LoginView;
import com.example.jwtsecurity.Views.SignupView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private JwtProvider jwtProvider;

    public AuthRestAPIs(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginView loginRequest){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);

        return ResponseEntity.ok(new OtherJwtResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupView signUpRequest){
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Username already taken!!"), HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Email already in use!!"), HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        var roleToAssign = signUpRequest.getRole();


        if(!roleToAssign.isBlank() && roleToAssign.equals("sonko")) {
            user.setRoles("ADMIN");
        }
        else {
            user.setRoles("USER");
        }

        userRepository.save(user);

        return new ResponseEntity<>(new ResponseMessage("User registered successfully"), HttpStatus.OK);
    }
}
