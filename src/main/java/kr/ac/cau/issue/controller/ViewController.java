package kr.ac.cau.issue.controller;

import jakarta.servlet.http.Cookie;
import kr.ac.cau.issue.controller.model.CommentDto;
import kr.ac.cau.issue.controller.model.IssueDto;
import kr.ac.cau.issue.controller.model.SimpleIssueDto;
import kr.ac.cau.issue.repository.ProjectRepository;
import kr.ac.cau.issue.repository.model.*;
import kr.ac.cau.issue.service.IssueService;
import kr.ac.cau.issue.service.ProjectService;
import kr.ac.cau.issue.service.SessionService;
import kr.ac.cau.issue.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class ViewController {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");

    private final IssueService issueService;
    private final UserService userService;
    private final SessionService sessionService;

    private final ProjectService projectService;
    private static final String REDIRECT_TO_LOGIN = "redirect:/login";


    private User getUserFromCookie(Cookie session) {
        try {
            UUID sessionId = UUID.fromString(session.getValue());
            return sessionService.getUserFromSession(sessionId);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/project")
    public String project(Model model, @CookieValue("session") Cookie session) {
        User user = getUserFromCookie(session);
        if (user == null) {
            return REDIRECT_TO_LOGIN;
        }

        model.addAttribute("projects", projectService.getProjects());

        return "project";
    }


    @GetMapping("/project/{projectId}")
    public String projectHome(
            Model model,
            @PathVariable String projectId,
            @CookieValue("session") Cookie session
    ) {
        User user = getUserFromCookie(session);
        if (user == null) {
            return REDIRECT_TO_LOGIN;
        }

        final String upperCaseProjectId = projectId.toUpperCase();
        List<SimpleIssueDto> issues = issueService
                .getSimpleIssuesByProjectId(projectId)
                .stream()
                .sorted(Comparator.comparing(SimpleIssue::getReportedAt).reversed())
                .map(issue -> {
                    String assigneeName = issue.getAssignee().map(User::getUsername).orElse(UserService.NOT_ASSIGNED_USER);

                    return new SimpleIssueDto(
                            issue.getId(),
                            String.format("%s-%d", upperCaseProjectId, issue.getId()),
                            issue.getTitle(),
                            issue.getStatus(),
                            issue.getPriority(),
                            assigneeName,
                            issue.getReportedAt().format(formatter)
                    );
                })
                .collect(Collectors.toList());

        List<String> users = userService.getAvailableAssignee();

        model.addAttribute("user", user);
        model.addAttribute("users", users);
        model.addAttribute("priority", IssuePriority.values());
        model.addAttribute("status", IssueStatus.values());

        model.addAttribute("issues", issues);

        return "home";
    }

    @GetMapping("/project/{projectId}/issue/{issueId}")
    public String issueDetail(
            Model model,
            @PathVariable String projectId,
            @PathVariable int issueId,
            @CookieValue("session") Cookie session
    ) {
        User user = getUserFromCookie(session);
        if (user == null) {
            return REDIRECT_TO_LOGIN;
        }

        Issue issue = issueService.getIssue(issueId).orElseThrow();

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

}
