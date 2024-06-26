package kr.ac.cau.issue.controller.model;

import kr.ac.cau.issue.repository.model.IssuePriority;
import kr.ac.cau.issue.repository.model.IssueStatus;

public record SimpleIssueDto(
        int rawId,
        String id,
        String title,
        IssueStatus status,
        IssuePriority priority,
        String assignee,
        String reportedAt
) {
}
