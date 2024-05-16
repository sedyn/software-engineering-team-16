package kr.ac.cau.issue.controller;

import kr.ac.cau.issue.controller.model.CommentDto;
import kr.ac.cau.issue.controller.model.IssueDto;
import kr.ac.cau.issue.controller.model.SimpleIssueDto;
import kr.ac.cau.issue.repository.model.Issue;
import kr.ac.cau.issue.repository.model.IssuePriority;
import kr.ac.cau.issue.repository.model.IssueStatus;
import kr.ac.cau.issue.repository.model.User;
import kr.ac.cau.issue.service.IssueService;
import kr.ac.cau.issue.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class ViewController {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");

    private final IssueService issueService;
    private final UserService userService;

    @GetMapping("/project/{projectId}")
    public String projectHome(Model model, @PathVariable String projectId) {
        final String upperCaseProjectId = projectId.toUpperCase();
        List<SimpleIssueDto> issues = issueService
                .getSimpleIssuesByProjectId(projectId)
                .stream()
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

        model.addAttribute("users", users);
        model.addAttribute("priority", IssuePriority.values());
        model.addAttribute("status", IssueStatus.values());

        model.addAttribute("issues", issues);

        return "home";
    }

    @GetMapping("/project/{projectId}/issue/{issueId}")
    public String issueDetail(Model model, @PathVariable String projectId, @PathVariable int issueId) {
        Issue issue = issueService.getIssue(issueId).orElseThrow();

        List<String> users = userService.getAvailableAssignee();
        List<CommentDto> comments = issue.getComments().stream().map(CommentDto::new).collect(Collectors.toList());

        model.addAttribute("users", users);
        model.addAttribute("priority", IssuePriority.values());
        model.addAttribute("status", IssueStatus.values());

        model.addAttribute("issue", new IssueDto(projectId, issue));
        model.addAttribute("comments", comments);

        return "issue";
    }

}
