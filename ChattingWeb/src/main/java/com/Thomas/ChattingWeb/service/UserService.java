package com.Thomas.ChattingWeb.service;


import com.Thomas.ChattingWeb.Exception.UserException;
import com.Thomas.ChattingWeb.model.User;
import request.UpdateUserRequest;

import java.util.List;


public interface UserService {
    public User findUserById (Integer id) throws UserException;
    public User updateUser(Integer userId, UpdateUserRequest req ) throws UserException;
    public User findUserProfile (String jwt) throws UserException;
    public List<User> searchUser(String keyword);
}
