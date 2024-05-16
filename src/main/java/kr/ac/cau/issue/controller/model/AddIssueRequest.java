package kr.ac.cau.issue.controller.model;

import kr.ac.cau.issue.repository.model.IssuePriority;
import kr.ac.cau.issue.repository.model.IssueStatus;

public record AddIssueRequest(
        String title,
        IssuePriority priority,
        IssueStatus status,
        String assignee
) {
}
