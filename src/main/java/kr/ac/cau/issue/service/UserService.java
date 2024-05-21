package kr.ac.cau.issue.service;

import kr.ac.cau.issue.repository.UserRepository;
import kr.ac.cau.issue.repository.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    public static final String NOT_ASSIGNED_USER = "Not Assigned";

    private final UserRepository userRepository;

    public Optional<User> getUser(int userId) {
        return userRepository.findById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAllByAdminFalse();
    }

    public List<String> getAvailableAssignee() {
        List<User> users = getAllUsers();
        List<String> names = users.stream().map(User::getUsername).collect(Collectors.toList());
        names.add(NOT_ASSIGNED_USER);

        return names;
    }

    public Optional<User> getUserByCredential(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

}
