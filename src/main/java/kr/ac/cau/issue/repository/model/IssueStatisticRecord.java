package kr.ac.cau.issue.repository.model;

import java.time.LocalDateTime;

public interface IssueStatisticRecord {

    LocalDateTime getDatetime();

    IssueStatus getStatus();

    int getIssueCount();

}
