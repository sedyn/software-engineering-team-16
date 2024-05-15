package kr.ac.cau.issue.repository.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity(name = "issues")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false)
    String title;
    String description;
    LocalDateTime reportedAt;

    @PrimaryKeyJoinColumn
    @ManyToOne
    Project project;

    @PrimaryKeyJoinColumn
    @OneToOne
    User reporter;

    @PrimaryKeyJoinColumn
    @OneToOne
    User assignee;

    @PrimaryKeyJoinColumn
    @OneToOne
    User fixer;

    @Enumerated(EnumType.STRING)
    IssueStatus status = IssueStatus.New;

    @Enumerated(EnumType.STRING)
    IssuePriority priority = IssuePriority.Major;

    @OneToMany(mappedBy = "issue")
    List<Comment> comments;
}
