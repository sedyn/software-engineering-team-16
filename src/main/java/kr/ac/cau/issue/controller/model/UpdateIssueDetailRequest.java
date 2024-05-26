package kr.ac.cau.issue.controller.model;

import kr.ac.cau.issue.repository.model.IssuePriority;
import kr.ac.cau.issue.repository.model.IssueStatus;

public record UpdateIssueDetailRequest(
        String description,
        String assignee,
        String fixer,
        IssuePriority priority,
        IssueStatus status
) {
}
