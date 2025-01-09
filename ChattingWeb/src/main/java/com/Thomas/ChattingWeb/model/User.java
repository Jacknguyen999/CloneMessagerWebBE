package com.Thomas.ChattingWeb.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String full_name;
    private String email;
    private String password;
    private String profile_pic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(full_name, user.full_name)
                && Objects.equals(email, user.email) && Objects.equals(password, user.password)
                && Objects.equals(profile_pic, user.profile_pic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, full_name, email, password, profile_pic);
    }
}
