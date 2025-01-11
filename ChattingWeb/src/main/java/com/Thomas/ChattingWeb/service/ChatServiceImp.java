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
import java.util.Optional;


@Service
public class ChatServiceImp implements ChatService {


    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserService userService;

    @Override
    public Chat createChat(User reqUser, Integer userId2) throws UserException {
        User user = userService.findUserById(userId2);

        // Check if chat already exists
        Chat existingChat = chatRepository.findingSingleChatByUserId(user, reqUser);

        if (existingChat != null) {
            return existingChat; // Return existing chat
        }

        // Create a new chat if no existing chat is found
        Chat chat = new Chat();
        chat.setCreatedBy(reqUser);
        chat.getAdmin().add(reqUser);
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        chat.setGroup(false);

        return chatRepository.save(chat);
    }


    @Override
    public Chat findChatById(Integer chatId) throws ChatException {
        Optional<Chat> chat = chatRepository.findById(chatId);

        if (chat.isPresent()){
            return chat.get();
        }

        throw new ChatException("Chat not found with id " + chatId );

    }

    @Override
    public List<Chat> findALlChatByUserId(Integer userId) throws UserException {
        User user = userService.findUserById(userId);

        List<Chat> chats = chatRepository.findChatByUserId(user.getId());

        return chats;
    }

    @Override
    public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException, ChatException {
        Chat group = new Chat();
        group.setGroup(true); // Mark as a group chat
        group.setChat_image(req.getChat_image());
        group.setChat_name(req.getChat_name());
        group.setCreatedBy(reqUser); // Set the creator
        group.getAdmin().add(reqUser); // Add creator as admin

        // Add the creator to the group participants
        group.getUsers().add(reqUser);

        // Add other members to the group
        for (Integer userId : req.getUserIds()) {
            User user = userService.findUserById(userId);
            group.getUsers().add(user);
        }

        // Save the group chat
        return chatRepository.save(group);
    }


    @Override
    public Chat addUsersToGroup(Integer chatId, Integer userIds, User reqUser) throws ChatException, UserException {

        Optional<Chat> opt = chatRepository.findById(chatId);
        User user = userService.findUserById(userIds);

        if( opt.isPresent()){
            Chat chat = opt.get();
            if( chat.getAdmin().contains(reqUser)){
                chat.getUsers().add(user);
                return chatRepository.save(chat);
            }
            else {
                throw new UserException("You are not admin of this group");
            }
        }
        throw new ChatException("Chat not found with id " + chatId );
    }

    @Override
    public Chat removeUsersFromGroup(Integer chatId, Integer userIds,User reqUser) throws ChatException, UserException {
        Optional<Chat> opt = chatRepository.findById(chatId);
        User user = userService.findUserById(userIds);

        if( opt.isPresent()){
            Chat chat = opt.get();
            if( chat.getAdmin().contains(reqUser) ){
                chat.getUsers().remove(user);
                return chatRepository.save(chat);
            } else if (chat.getUsers().contains(reqUser)) {
                if ( user.getId().equals(reqUser.getId())){
                    chat.getUsers().remove(user);
                    return chatRepository.save(chat);
                }
                
            }
                throw new UserException("You cant remove user from this group");

        }
        throw new ChatException("Chat not found with id " + chatId );

    }

    @Override
    public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {

        Optional<Chat> chat = chatRepository.findById(chatId);

        if ( chat.isPresent()) {
            Chat chat1 = chat.get();
            chatRepository.deleteById(chat1.getChatId());
        }



    }

    @Override
    public Chat renameGroup(Integer chatId,String groupName, User reqUser) throws ChatException, UserException {
        Optional<Chat> opt = chatRepository.findById(chatId);

        if ( opt.isPresent()) {
            Chat chat = opt.get();
            if(chat.getAdmin().contains(reqUser)){
                chat.setChat_name(groupName);
                return chatRepository.save(chat);
            }
            else {
                throw new UserException("You are not admin of this group");
            }
        }
        throw new ChatException("Chat not found with id " + chatId );
    }
}
