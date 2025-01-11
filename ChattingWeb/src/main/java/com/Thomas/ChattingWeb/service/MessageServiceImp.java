package com.Thomas.ChattingWeb.service;

import com.Thomas.ChattingWeb.Exception.ChatException;
import com.Thomas.ChattingWeb.Exception.UserException;
import com.Thomas.ChattingWeb.model.Chat;
import com.Thomas.ChattingWeb.model.Message;
import com.Thomas.ChattingWeb.model.User;
import com.Thomas.ChattingWeb.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import request.SendmessageRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class MessageServiceImp implements MessageService{

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;


    @Override
    public Message sendMessage(SendmessageRequest req) throws ChatException, UserException {

        User user = userService.findUserById(req.getUserId());
        Chat chat = chatService.findChatById(req.getChatId());

        Message message = new Message();
        message.setChat(chat);
        message.setSender(user);
        message.setContent(req.getMessage());
        message.setTimestamp(LocalDateTime.now());
        chat.setMessages(Collections.singletonList(message));


        return messageRepository.save(message);
    }

    @Override
    public List<Message> getChatsMessage(Integer chatId , User reqUser) throws ChatException {

        Chat chat = chatService.findChatById(chatId);
        if ( !chat.getUsers().contains(reqUser)){
            throw new ChatException("User is not in the chat" + chat.getChatId());
        }

        List<Message> messages = messageRepository.findByChatId(chat.getChatId());

        return messages;
    }

    @Override
    public Message findMessageById(Integer messageId) throws ChatException {
        Optional<Message> opt = messageRepository.findById(messageId);

        if ( opt.isPresent()){
            return opt.get();
        }
        throw new ChatException("Message not found" + messageId);
    }

    @Override
    public void deleteMessage(Integer messageId, User reqUser) throws ChatException {
        Message message = findMessageById(messageId);

        if ( message.getSender().getId().equals(reqUser.getId())){
            messageRepository.delete(message);
        }

        throw new ChatException("User is not the sender of the message" +reqUser.getFull_name());

    }
}
