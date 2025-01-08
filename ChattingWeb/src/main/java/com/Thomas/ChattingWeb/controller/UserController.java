package com.Thomas.ChattingWeb.controller;


import com.Thomas.ChattingWeb.Exception.UserException;
import com.Thomas.ChattingWeb.model.User;
import com.Thomas.ChattingWeb.response.ApiResponse;
import com.Thomas.ChattingWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import request.UpdateUserRequest;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(
       @RequestHeader("Authorization") String token
    ) throws UserException {
        User user = userService.findUserProfile(token);

        return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);

    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<User>> searchUserHandler(
            @PathVariable("keyword") String key
    )
    {
        List<User> users = userService.searchUser(key);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUserHandler (
            @RequestBody UpdateUserRequest req ,@RequestHeader("Authorization") String token
            ) throws UserException {
        User user = userService.findUserProfile(token);

        userService.updateUser(user.getId(), req);


        return new ResponseEntity<>(new ApiResponse("User updated successfully",true), HttpStatus.ACCEPTED);

    }


}
