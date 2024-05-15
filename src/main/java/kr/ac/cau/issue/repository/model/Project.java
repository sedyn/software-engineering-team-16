package kr.ac.cau.issue.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "projects")
public class Project {
    @Id
    String id;
    String name;
}
