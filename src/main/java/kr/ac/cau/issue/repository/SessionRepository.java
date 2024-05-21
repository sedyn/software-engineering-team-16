package kr.ac.cau.issue.repository;

import kr.ac.cau.issue.repository.model.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SessionRepository extends CrudRepository<Session, UUID> {
}
