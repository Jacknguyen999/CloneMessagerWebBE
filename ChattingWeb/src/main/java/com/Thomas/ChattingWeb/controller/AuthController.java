package com.Thomas.ChattingWeb.controller;


import com.Thomas.ChattingWeb.Exception.UserException;
import com.Thomas.ChattingWeb.config.JwtProvider;
import com.Thomas.ChattingWeb.model.User;
import com.Thomas.ChattingWeb.repository.UserRepository;
import com.Thomas.ChattingWeb.response.AuthResponse;
import com.Thomas.ChattingWeb.service.CustomUserService;
import com.Thomas.ChattingWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import request.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserService customUserService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(
            @RequestBody User user
    ) throws UserException {

        String email = user.getEmail();
        String fullname = user.getFull_name();
        String password = user.getPassword();

        User isUser = userRepository.findByEmail(email);
        if ( isUser != null ){
            throw new UserException("User already exists with email: "+email);

        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setFull_name(fullname);
        createdUser.setPassword(passwordEncoder.encode(password));

        User savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse res = new AuthResponse(jwt,true);
        return ResponseEntity.ok(res);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(
        @RequestBody LoginRequest req
    ){
        String email = req.getEmail();
        String password = req.getPassword();

        Authentication authentication = authentication(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse(jwt,true);
        return ResponseEntity.ok(res);

    }

    public Authentication authentication(String Username, String password){

        UserDetails userDetails = customUserService.loadUserByUsername(Username);

        if ( userDetails  == null) {
            throw new BadCredentialsException("User not found with email: "+Username);
        }

        if (!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }



}
