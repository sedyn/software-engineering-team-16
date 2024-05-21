package kr.ac.cau.issue.service;

import kr.ac.cau.issue.repository.SessionRepository;
import kr.ac.cau.issue.repository.model.Session;
import kr.ac.cau.issue.repository.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public User getUserFromSession(UUID sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow().getUser();
    }

    public Session createSession(User user) {
        Session session = new Session();
        session.setId(UUID.randomUUID());
        session.setUser(user);

        return sessionRepository.save(session);
    }
}
