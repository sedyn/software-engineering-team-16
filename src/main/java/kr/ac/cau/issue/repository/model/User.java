package kr.ac.cau.issue.repository.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "users")
public class User {

    public static final String NOT_ASSIGNED_USER = "Not Assigned";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean admin = false;
    private String username;
    private String password;
}
