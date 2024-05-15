package kr.ac.cau.issue.repository.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Issue {
    @Id
    int id;
    @Column(nullable = false)
    String title;
    String description;
    LocalDateTime reportedAt;

    String projectId;

    @Column(nullable = false)
    @OneToOne
    User reporter;

    @OneToOne
    User assignee;

    @OneToOne
    User fixer;

    @Enumerated(EnumType.STRING)
    IssueStatus status = IssueStatus.New;

    @Enumerated(EnumType.STRING)
    IssuePriority priority = IssuePriority.Major;

    @OneToMany(mappedBy = "issue")
    List<Comment> comments;
}
