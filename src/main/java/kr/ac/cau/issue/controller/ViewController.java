package kr.ac.cau.issue.controller;

import kr.ac.cau.issue.controller.model.SimpleIssueDto;
import kr.ac.cau.issue.repository.model.User;
import kr.ac.cau.issue.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class ViewController {

    private final IssueService issueService;

    @GetMapping("/project/{projectId}")
    public String projectHome(Model model, @PathVariable String projectId) {
        List<SimpleIssueDto> issues = issueService
                .getSimpleIssuesByProjectId(projectId)
                .stream()
                .map(issue -> {
                    String assigneeName = issue.getAssignee().map(User::getUsername).orElse("지정되지 않음");

                    return new SimpleIssueDto(
                            issue.getId(),
                            issue.getTitle(),
                            issue.getStatus(),
                            issue.getPriority(),
                            assigneeName,
                            issue.getReportedAt()
                            );
                })
                .collect(Collectors.toList());

        model.addAttribute("issues", issues);

        return "home";
    }

}
