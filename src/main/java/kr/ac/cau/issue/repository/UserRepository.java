package kr.ac.cau.issue.repository;

import kr.ac.cau.issue.repository.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
