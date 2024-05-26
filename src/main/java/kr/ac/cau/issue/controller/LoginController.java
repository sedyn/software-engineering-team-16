package kr.ac.cau.issue.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.cau.issue.controller.model.LoginRequest;
import kr.ac.cau.issue.controller.model.RegisterRequest;
import kr.ac.cau.issue.repository.model.Session;
import kr.ac.cau.issue.repository.model.User;
import kr.ac.cau.issue.service.SessionService;
import kr.ac.cau.issue.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final UserService userService;

    private final SessionService sessionService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("session", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/login";
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postLogin(HttpServletResponse response, LoginRequest request) {
        Optional<User> optionalUser = userService.getUserByCredential(request.getUsername(), request.getPassword());

        if (optionalUser.isEmpty()) {
            return "redirect:/login";
        }

        User user = optionalUser.orElseThrow();

        Session session = sessionService.createSession(user);

        Cookie cookie = new Cookie("session", session.getId().toString());
        response.addCookie(cookie);
        return "redirect:/project";
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String register(RegisterRequest request) {
        userService.addUser(request.getUsername(), request.getPassword());
        return "redirect:/project";
    }

}
