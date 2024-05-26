package kr.ac.cau.issue.controller.model;

import kr.ac.cau.issue.controller.ProjectController;
import kr.ac.cau.issue.repository.model.Issue;
import kr.ac.cau.issue.repository.model.IssuePriority;
import kr.ac.cau.issue.repository.model.IssueStatus;
import lombok.Getter;

@Getter
public class IssueDto {

    private final int rawId;

    private final String id;

    private final String title;
    private final String description;

    private final IssuePriority priority;

    private final IssueStatus status;

    private final String assignee;

    private final String reporter;

    private final String reportedAt;

    public IssueDto(String projectId, Issue issue) {
        rawId = issue.getId();
        id = String.format("%s-%d", projectId, issue.getId());
        title = issue.getTitle();
        description = issue.getDescription();
        priority = issue.getPriority();
        status = issue.getStatus();
        assignee = issue.getAssigneeName();
        reporter = issue.getReporter().getUsername();
        reportedAt = issue.getReportedAt().format(ProjectController.formatter);
    }

}
