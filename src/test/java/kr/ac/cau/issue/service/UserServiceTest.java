package kr.ac.cau.issue.service;

import kr.ac.cau.issue.repository.UserRepository;
import kr.ac.cau.issue.repository.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetAllUsers() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        user1.setId(1);
        user2.setId(2);
        user3.setId(3);

        List<User> expected = List.of(user1, user2, user3);
        when(userRepository.findAllByAdminFalse()).thenReturn(expected);
        List<User> actual = userService.getAllUsers();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetAvailableAssignee() {
        User user1 = new User();
        User user2 = new User();

        user1.setUsername("A");
        user2.setUsername("B");

        List<String> expected = List.of("A", "B", User.NOT_ASSIGNED_USER);
        when(userRepository.findAllByAdminFalse()).thenReturn(List.of(user1, user2));
        List<String> actual = userService.getAvailableAssignee();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetUserByCredential() {
        User excepted = new User();

        excepted.setUsername("A");

        when(userRepository.findByUsernameAndPassword("A", "password")).thenReturn(Optional.of(excepted));
        Optional<User> actual = userService.getUserByCredential("A", "password");

        assertEquals(excepted, actual.orElseThrow());
    }

    @Test
    public void testGetUserByCredentialWithInvalidPassword() {
        when(userRepository.findByUsernameAndPassword("A", "drowssap")).thenReturn(Optional.empty());
        Optional<User> actual = userService.getUserByCredential("A", "drowssap");

        assertEquals(Optional.empty(), actual);
    }

}
