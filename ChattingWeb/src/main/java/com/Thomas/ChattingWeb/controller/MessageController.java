package com.Thomas.ChattingWeb.controller;


import com.Thomas.ChattingWeb.Exception.ChatException;
import com.Thomas.ChattingWeb.Exception.MessageException;
import com.Thomas.ChattingWeb.Exception.UserException;
import com.Thomas.ChattingWeb.model.Message;
import com.Thomas.ChattingWeb.model.User;
import com.Thomas.ChattingWeb.response.ApiResponse;
import com.Thomas.ChattingWeb.service.MessageService;
import com.Thomas.ChattingWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import request.SendmessageRequest;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;


    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(
            @RequestBody SendmessageRequest req,
            @RequestHeader("Authorization") String token
            ) throws ChatException, UserException {


        User user = userService.findUserProfile(token);
        req.setUserId(user.getId());
        Message message = messageService.sendMessage(req);

        return ResponseEntity.ok(message);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getMessage(
            @PathVariable Integer chatId,
            @RequestHeader("Authorization") String token
    ) throws ChatException, UserException {


        User user = userService.findUserProfile(token);

        List<Message> message = messageService.getChatsMessage(chatId, user);

        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/chat/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessage(
            @PathVariable Integer messageId,
            @RequestHeader("Authorization") String token
    ) throws UserException,  ChatException {


        User user = userService.findUserProfile(token);

       messageService.deleteMessage(messageId, user);

       ApiResponse res = new ApiResponse("Message Deleted", false);

        return ResponseEntity.ok(res);
    }

}
