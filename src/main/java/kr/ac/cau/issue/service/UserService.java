package kr.ac.cau.issue.service;

import kr.ac.cau.issue.repository.UserRepository;
import kr.ac.cau.issue.repository.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> getUser(int userId) {
        return userRepository.findById(userId);
    }

}
