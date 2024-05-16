package kr.ac.cau.issue.repository;

import kr.ac.cau.issue.repository.model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
}
