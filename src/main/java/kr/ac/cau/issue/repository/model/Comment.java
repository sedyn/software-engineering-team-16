package kr.ac.cau.issue.repository.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String content;
    LocalDateTime createdAt;

    @ManyToOne
    User user;

    @ManyToOne
    Issue issue;
}
