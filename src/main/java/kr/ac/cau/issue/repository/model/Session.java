package kr.ac.cau.issue.repository.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;

import java.util.UUID;

@Data
@Entity(name = "sessions")
public class Session {

    @Id
    UUID id;

    @PrimaryKeyJoinColumn
    @ManyToOne
    User user;

}
