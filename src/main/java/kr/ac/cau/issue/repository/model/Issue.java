package kr.ac.cau.issue.repository.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(
        name = "issues",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "ISSUE_ID",
                        columnNames = {"issue_id", "project_id"}
                )
        })
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false)
    String title;
    String description;
    LocalDateTime reportedAt;

    int issueId;

    @PrimaryKeyJoinColumn
    @ManyToOne
    Project project;

    @PrimaryKeyJoinColumn
    @ManyToOne
    User reporter;

    @PrimaryKeyJoinColumn
    @ManyToOne
    User assignee;

    @PrimaryKeyJoinColumn
    @ManyToOne
    User fixer;

    @Enumerated(EnumType.STRING)
    IssueStatus status = IssueStatus.New;

    @Enumerated(EnumType.STRING)
    IssuePriority priority = IssuePriority.Major;

    @OneToMany(mappedBy = "issue")
    List<Comment> comments;

    public String getAssigneeName() {
        if (assignee != null) {
            return assignee.getUsername();
        } else {
            return "Not Assigned";
        }
    }

    public String getFixerName() {
        if (fixer != null) {
            return fixer.getUsername();
        } else {
            return "Not Assigned";
        }
    }
}
