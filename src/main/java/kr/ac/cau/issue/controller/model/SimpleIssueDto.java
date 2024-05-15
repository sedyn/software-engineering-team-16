package kr.ac.cau.issue.controller.model;

import kr.ac.cau.issue.repository.model.IssuePriority;
import kr.ac.cau.issue.repository.model.IssueStatus;

import java.time.LocalDateTime;

public record SimpleIssueDto(
        int id,
        String title,
        IssueStatus status,
        IssuePriority priority,
        String assignee,
        LocalDateTime reportedAt
) {
}
