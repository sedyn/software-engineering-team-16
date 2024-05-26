package kr.ac.cau.issue.controller;

import kr.ac.cau.issue.controller.model.AddProjectRequest;
import kr.ac.cau.issue.controller.model.SimpleIssueDto;
import kr.ac.cau.issue.controller.resolver.UserSession;
import kr.ac.cau.issue.repository.model.*;
import kr.ac.cau.issue.service.IssueService;
import kr.ac.cau.issue.service.ProjectService;
import kr.ac.cau.issue.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static kr.ac.cau.issue.repository.model.User.NOT_ASSIGNED_USER;

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
    public String projectHome(
            Model model,
            @PathVariable String projectId,
            @UserSession User user,
            @RequestParam(required = false) String assignee,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String reporter
    ) {
        final String upperCaseProjectId = projectId.toUpperCase();
        List<SimpleIssueDto> issues = issueService
                .getSimpleIssuesByProjectId(
                        projectId,
                        assignee,
                        reporter,
                        status,
                        priority
                )
                .stream()
                .sorted(Comparator.comparing(Issue::getReportedAt).reversed())
                .map(issue -> {
                    String assigneeName = issue.getAssigneeName();

                    return new SimpleIssueDto(
                            issue.getIssueId(),
                            String.format("%s-%d", upperCaseProjectId, issue.getIssueId()),
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

    @PostMapping("")
    public String addProject(AddProjectRequest request) {
        projectService.addProject(request.getId(), request.getDescription());
        return "redirect:/project";
    }
}
