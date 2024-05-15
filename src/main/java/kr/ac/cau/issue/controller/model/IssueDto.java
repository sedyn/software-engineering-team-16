package kr.ac.cau.issue.controller.model;

import kr.ac.cau.issue.repository.model.Issue;
import kr.ac.cau.issue.repository.model.IssuePriority;
import kr.ac.cau.issue.repository.model.IssueStatus;
import lombok.Getter;

@Getter
public class IssueDto {

    private final int id;
    private final String title;
    private final String description;

    private final IssuePriority priority;

    private final IssueStatus status;

    public IssueDto(Issue issue) {
        id = issue.getId();
        title = issue.getTitle();
        description = issue.getDescription();
        priority = issue.getPriority();
        status = issue.getStatus();
    }

}
