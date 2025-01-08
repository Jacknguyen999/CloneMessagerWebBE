package com.Thomas.ChattingWeb.service;


import com.Thomas.ChattingWeb.Exception.UserException;
import com.Thomas.ChattingWeb.config.JwtProvider;
import com.Thomas.ChattingWeb.model.User;
import com.Thomas.ChattingWeb.repository.UserRepository;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import request.UpdateUserRequest;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplement implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;


    @Override
    public User findUserById(Integer id) throws UserException {

        Optional<User> opt = userRepository.findById(id);

        if (opt.isPresent()){
            return opt.get();
        }
        throw new UserException("User not found with id: "+id);
    }

    @Override
    public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
        User user = findUserById(userId);

        if (req.getFull_name() != null){
            user.setFull_name(req.getFull_name());
        }

        return null;
    }

    @Override
    public User findUserProfile(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        if ( email == null ){
            throw  new BadCredentialsException("Invalid JWT token");
        }
        User user = userRepository.findByEmail(email);
        if ( user == null ){
            throw  new UserException("Invalid JWT token");
        }

        return user;
    }

    @Override
    public List<User> searchUser(String keyword) {
        List<User> users = userRepository.searchUser(keyword);
        return users;
    }
}
