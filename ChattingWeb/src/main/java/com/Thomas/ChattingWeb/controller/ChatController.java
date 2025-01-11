package com.Thomas.ChattingWeb.controller;


import com.Thomas.ChattingWeb.Exception.ChatException;
import com.Thomas.ChattingWeb.Exception.UserException;
import com.Thomas.ChattingWeb.model.Chat;
import com.Thomas.ChattingWeb.model.User;
import com.Thomas.ChattingWeb.response.ApiResponse;
import com.Thomas.ChattingWeb.service.ChatService;
import com.Thomas.ChattingWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import request.GroupChatRequest;
import request.SingleChatRequest;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;


    @PostMapping("/single")
    public ResponseEntity<Chat> createChat(
            @RequestBody SingleChatRequest req,
            @RequestHeader("Authorization") String token
    ) throws UserException {
        User reqUser = userService.findUserProfile(token);

        Chat chat = chatService.createChat(reqUser, req.getUserId());

        return ResponseEntity.ok(chat);
    }

    @PostMapping("/group")
    public ResponseEntity<Chat> createGroupChat(
            @RequestBody GroupChatRequest req,
            @RequestHeader("Authorization") String token
    ) throws UserException, ChatException {
        User reqUser = userService.findUserProfile(token);

        Chat chat = chatService.createGroup(req, reqUser);

        return ResponseEntity.ok(chat);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> findChatById(
            @PathVariable Integer chatId,
            @RequestHeader("Authorization") String token
    ) throws  ChatException {

        Chat chat = chatService.findChatById(chatId);
        return ResponseEntity.ok(chat);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Chat>> GetAllChat(
            @RequestHeader("Authorization") String token
    ) throws UserException {

        User reqUser = userService.findUserProfile(token);
        List<Chat> chats = chatService.findALlChatByUserId(reqUser.getId());
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }

    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> AddUserToChat(
            @PathVariable Integer chatId,
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String token
    ) throws UserException, ChatException {

        User reqUser = userService.findUserProfile(token);
        Chat chats = chatService.addUsersToGroup(chatId, userId, reqUser);
        return ResponseEntity.ok(chats);
    }

    @DeleteMapping("/{chatId}/remove/{userId}")
    public ResponseEntity<Chat> RemoveFromChat  (
            @PathVariable Integer chatId,
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String token
    ) throws UserException, ChatException {

        User reqUser = userService.findUserProfile(token);
        Chat chats = chatService.removeUsersFromGroup(chatId, userId, reqUser);
        return ResponseEntity.ok(chats);
    }

    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<ApiResponse> DeleteChatHandler(
            @PathVariable Integer chatId,
            @RequestHeader("Authorization") String token
    ) throws UserException, ChatException {

        User reqUser = userService.findUserProfile(token);
        chatService.deleteChat(chatId, reqUser.getId());
        ApiResponse res = new ApiResponse("Chat deleted ", false);

        return ResponseEntity.ok(res);
    }



}
