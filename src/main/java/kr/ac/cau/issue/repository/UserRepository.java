package kr.ac.cau.issue.repository;

import kr.ac.cau.issue.repository.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findAllByAdminFalse();

}
