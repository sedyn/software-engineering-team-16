package kr.ac.cau.issue.controller;

import kr.ac.cau.issue.controller.model.*;
import kr.ac.cau.issue.controller.resolver.UserSession;
import kr.ac.cau.issue.repository.model.*;
import kr.ac.cau.issue.service.CommentService;
import kr.ac.cau.issue.service.IssueService;
import kr.ac.cau.issue.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/project/{projectId}")
public class IssueController {

    private final UserService userService;
    private final IssueService issueService;
    private final CommentService commentService;

    @PostMapping(value = "/issue", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addIssue(
            @PathVariable String projectId,
            @UserSession User user,
            AddIssueRequest request
    ) {
        issueService.addIssue(user, projectId, request);
        return "redirect:/project/" + projectId;
    }

    @PostMapping(value = "/issue/{issueId}/comment", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addComment(
            @PathVariable String projectId,
            @PathVariable int issueId,
            @UserSession User user,
            AddCommentRequest request
    ) {
        Comment comment = new Comment();
        Issue issue = issueService.getIssue(issueId, projectId).orElseThrow();

        comment.setIssue(issue);
        comment.setUser(user);

        comment.setContent(request.content());
        comment.setCreatedAt(LocalDateTime.now());

        commentService.addComment(comment);
        return "redirect:/project/" + projectId + "/issue/" + issueId;
    }

    @GetMapping("/issue/{issueId}")
    public String issueDetail(Model model, @PathVariable String projectId, @PathVariable int issueId, @UserSession User user) {
        Issue issue = issueService.getIssue(issueId, projectId).orElseThrow();

        List<String> users = userService.getAvailableAssignee();
        List<CommentDto> comments = issue
                .getComments()
                .stream()
                .sorted(Comparator.comparing(Comment::getCreatedAt).reversed())
                .map(CommentDto::new)
                .collect(Collectors.toList());

        model.addAttribute("user", user);
        model.addAttribute("users", users);
        model.addAttribute("priority", IssuePriority.values());
        model.addAttribute("status", IssueStatus.values());

        model.addAttribute("issue", new IssueDto(projectId, issue));
        model.addAttribute("comments", comments);

        return "issue";
    }

    @PostMapping("/issue/{issueId}")
    public String updateDetail(
            @PathVariable String projectId,
            @PathVariable int issueId,
            UpdateIssueDetailRequest request) {
        issueService.updateIssueDetail(
                projectId,
                issueId,
                request.description(),
                request.fixer(),
                request.assignee(),
                request.status(),
                request.priority()
        );

        return "redirect:/project/" + projectId + "/issue/" + issueId;
    }

    @GetMapping("/statistic")
    public String statistic(Model model, @PathVariable String projectId, @UserSession User user) {
        Map<String, Map<IssueStatus, Integer>> statistic = issueService.getIssueStatistic(projectId);

        model.addAttribute("user", user);
        model.addAttribute("statistic", statistic);
        return "statistic";
    }
}
