package kr.ac.cau.issue.controller;

import kr.ac.cau.issue.controller.model.SimpleIssueDto;
import kr.ac.cau.issue.controller.resolver.UserSession;
import kr.ac.cau.issue.repository.model.IssuePriority;
import kr.ac.cau.issue.repository.model.IssueStatus;
import kr.ac.cau.issue.repository.model.SimpleIssue;
import kr.ac.cau.issue.repository.model.User;
import kr.ac.cau.issue.service.IssueService;
import kr.ac.cau.issue.service.ProjectService;
import kr.ac.cau.issue.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/project")
public class ProjectController {

    private final IssueService issueService;

    private final ProjectService projectService;

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");

    private final UserService userService;

    @GetMapping("")
    public String project(Model model, @UserSession User user) {
        model.addAttribute("user", user);
        model.addAttribute("projects", projectService.getProjects());
        return "project";
    }

    @GetMapping("/{projectId}")
    public String projectHome(Model model, @PathVariable String projectId, @UserSession User user) {
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
}
