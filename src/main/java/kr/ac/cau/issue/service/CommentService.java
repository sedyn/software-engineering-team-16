package kr.ac.cau.issue.service;

import kr.ac.cau.issue.repository.CommentRepository;
import kr.ac.cau.issue.repository.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

}
