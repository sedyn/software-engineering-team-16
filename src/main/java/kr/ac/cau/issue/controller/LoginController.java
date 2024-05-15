package kr.ac.cau.issue.controller;

import kr.ac.cau.issue.controller.model.LoginRequest;
import kr.ac.cau.issue.controller.model.LoginResponse;
import kr.ac.cau.issue.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return new LoginResponse(false, null);
    }

}
