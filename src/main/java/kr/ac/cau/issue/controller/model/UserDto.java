package kr.ac.cau.issue.controller.model;

import kr.ac.cau.issue.repository.model.User;
import lombok.Getter;

@Getter
public class UserDto {

    private final String username;

    public UserDto(User user) {
        username = user.getUsername();
    }

}
