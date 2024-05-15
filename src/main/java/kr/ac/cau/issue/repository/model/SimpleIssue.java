package kr.ac.cau.issue.repository.model;

public interface SimpleIssue {
    IssueStatus getStatus();
    IssuePriority getPriority();
    String getTitle();
    User getAssignee();
}
