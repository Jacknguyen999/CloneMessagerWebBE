package com.Thomas.ChattingWeb.service;


import com.Thomas.ChattingWeb.Exception.ChatException;
import com.Thomas.ChattingWeb.Exception.UserException;
import com.Thomas.ChattingWeb.model.Message;
import com.Thomas.ChattingWeb.model.User;
import request.SendmessageRequest;

import java.util.List;

public interface MessageService {
     Message sendMessage(SendmessageRequest req) throws ChatException, UserException;

     List<Message> getChatsMessage(Integer chatId, User reqUser) throws ChatException;

     Message findMessageById(Integer messageId) throws ChatException;

     void deleteMessage(Integer messageId, User reqUser) throws ChatException;


}
