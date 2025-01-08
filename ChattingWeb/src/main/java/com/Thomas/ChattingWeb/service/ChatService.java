package com.Thomas.ChattingWeb.service;

import com.Thomas.ChattingWeb.Exception.ChatException;
import com.Thomas.ChattingWeb.Exception.UserException;
import com.Thomas.ChattingWeb.model.Chat;
import com.Thomas.ChattingWeb.model.User;
import request.GroupChatRequest;

import java.util.List;

public interface ChatService {

    public Chat createChat(User reqUser , Integer userId2) throws UserException;

    public Chat findChatById(Integer chatId) throws ChatException;

    public List<Chat> findALlChatByUserId(Integer userId) throws UserException;

    public Chat createGroup(GroupChatRequest req, Integer userId) throws UserException, ChatException;

    public Chat addUsersToGroup(Integer chatId, List<Integer> userIds) throws ChatException, UserException;

    public Chat removeUsersFromGroup(Integer chatId, List<Integer> userIds) throws ChatException, UserException;

    public Chat deleteChat(Integer chatId,Integer userId) throws ChatException,UserException;

    public Chat renameGroup(Integer userId,String groupName, Integer reqUserId) throws ChatException, UserException;



}
