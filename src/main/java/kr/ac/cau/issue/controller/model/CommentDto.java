package kr.ac.cau.issue.controller.model;

import kr.ac.cau.issue.controller.ViewController;
import kr.ac.cau.issue.repository.model.Comment;
import lombok.Getter;

@Getter
public class CommentDto {

    private final String commenter;

    private final String content;

    private final String createdAt;

    public CommentDto(Comment comment) {
        commenter = comment.getUser().getUsername();
        content = comment.getContent();
        createdAt = comment.getCreatedAt().format(ViewController.formatter);
    }

}
