package kr.ac.cau.issue.repository;

import kr.ac.cau.issue.repository.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findAllByAdminFalse();

    Optional<User> findByUsernameAndPassword(String username, String password);

}
