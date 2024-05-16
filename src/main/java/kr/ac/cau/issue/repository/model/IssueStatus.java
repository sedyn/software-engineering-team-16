package kr.ac.cau.issue.repository.model;

public enum IssueStatus {
    New,
    Assigned,
    Resolved,
    Closed,
    Reopened;

    public boolean isDefault() {
        return this == New;
    }
}
