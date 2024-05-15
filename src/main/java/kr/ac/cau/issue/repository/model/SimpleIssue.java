package kr.ac.cau.issue.repository.model;

public interface SimpleIssue {
    IssueStatus getIssueStatus();
    IssuePriority getIssuePriority();
    String getTitle();
    User getAssignee();
}
