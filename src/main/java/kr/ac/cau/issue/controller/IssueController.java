package kr.ac.cau.issue.controller;

import kr.ac.cau.issue.controller.model.AddCommentRequest;
import kr.ac.cau.issue.controller.model.AddIssueRequest;
import kr.ac.cau.issue.repository.CommentRepository;
import kr.ac.cau.issue.repository.model.Comment;
import kr.ac.cau.issue.repository.model.Issue;
import kr.ac.cau.issue.repository.model.User;
import kr.ac.cau.issue.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
@RequestMapping("/project/{projectId}")
public class IssueController {

    private final IssueService issueService;

    private final CommentRepository commentRepository;

    @PostMapping(value = "/issue", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addIssue(@PathVariable String projectId, AddIssueRequest request) {
        issueService.addIssue(projectId, request);
        return "redirect:/project/" + projectId;
    }

    @PostMapping(value = "/issue/{issueId}/comment", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addComment(@PathVariable String projectId, @PathVariable int issueId, AddCommentRequest request) {
        Comment comment = new Comment();

        Issue issue = new Issue();
        issue.setId(issueId);
        comment.setIssue(issue);

        User user = new User();
        user.setId(1);
        comment.setUser(user);

        comment.setContent(request.content());
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);
        return "redirect:/project/" + projectId + "/issue/" + issueId;
    }

}
