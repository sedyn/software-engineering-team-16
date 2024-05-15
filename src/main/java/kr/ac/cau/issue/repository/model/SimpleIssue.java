package kr.ac.cau.issue.repository.model;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SimpleIssue {
    int getId();
    IssueStatus getStatus();
    IssuePriority getPriority();
    String getTitle();
    Optional<User> getAssignee();
    LocalDateTime getReportedAt();
}
