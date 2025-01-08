package com.Thomas.ChattingWeb.repository;

import com.Thomas.ChattingWeb.model.Chat;
import com.Thomas.ChattingWeb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ChatRepository extends JpaRepository<Chat,Integer> {


    @Query("SELECT c FROM Chat c join c.users u WHERE u.id = :userId")
    public List<Chat> findChatByUserId(
            @Param("userId")
            Integer userId);

    @Query("SELECT c FROM Chat c WHERE c.isGroup = false AND :user member  " +
            "of c.users And :reqUser member of c.users")
    public Chat findingSingleChatByUserId(
            @Param("user") User user ,
            @Param("reqUser") User reqUser
            );
}
