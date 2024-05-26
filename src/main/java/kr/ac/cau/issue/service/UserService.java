package kr.ac.cau.issue.service;

import kr.ac.cau.issue.repository.UserRepository;
import kr.ac.cau.issue.repository.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kr.ac.cau.issue.repository.model.User.NOT_ASSIGNED_USER;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> getUser(int userId) {
        return userRepository.findById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAllByAdminFalse();
    }

    public void addUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAdmin(false);
        userRepository.save(user);
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
