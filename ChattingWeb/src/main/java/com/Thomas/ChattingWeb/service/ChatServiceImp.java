package com.Thomas.ChattingWeb.service;


import com.Thomas.ChattingWeb.Exception.ChatException;
import com.Thomas.ChattingWeb.Exception.UserException;
import com.Thomas.ChattingWeb.model.Chat;
import com.Thomas.ChattingWeb.model.User;
import com.Thomas.ChattingWeb.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import request.GroupChatRequest;

import java.util.List;

@Service
public class ChatServiceImp implements ChatService {


    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserService userService;

    @Override
    public Chat createChat(User reqUser , Integer userId2) throws UserException {
        User user = userService.findUserById(userId2);

        Chat isChatExist = chatRepository.findingSingleChatByUserId(user,reqUser);

        if(isChatExist != null){
            return isChatExist;
        }

        Chat chat = new Chat();
        chat.setCreatedBy(reqUser);
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        chat.setGroup(false);


        return chat;
    }

    @Override
    public Chat findChatById(Integer chatId) throws ChatException {
        return null;
    }

    @Override
    public List<Chat> findALlChatByUserId(Integer userId) throws UserException {
        return List.of();
    }

    @Override
    public Chat createGroup(GroupChatRequest req, Integer userId) throws UserException, ChatException {
        return null;
    }

    @Override
    public Chat addUsersToGroup(Integer chatId, List<Integer> userIds) throws ChatException, UserException {
        return null;
    }

    @Override
    public Chat removeUsersFromGroup(Integer chatId, List<Integer> userIds) throws ChatException, UserException {
        return null;
    }

    @Override
    public Chat deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
        return null;
    }

    @Override
    public Chat renameGroup(Integer userId, String groupName, Integer reqUserId) throws ChatException, UserException {
        return null;
    }
}
