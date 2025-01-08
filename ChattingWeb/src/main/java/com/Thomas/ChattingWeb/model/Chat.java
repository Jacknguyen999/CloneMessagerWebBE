package com.Thomas.ChattingWeb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer chatId;
    private String chat_name;
    private String chat_image;


    @JoinColumn(name = "is_group")
    private boolean isGroup;


    @JoinColumn(name = "created_by")
    @ManyToOne
    private User createdBy;


    @ManyToMany
    private Set<User> users = new HashSet<>();


    @OneToMany
    private List<Message> messages = new ArrayList<>();


}