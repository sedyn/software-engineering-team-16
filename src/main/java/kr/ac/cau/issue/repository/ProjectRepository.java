package kr.ac.cau.issue.repository;

import kr.ac.cau.issue.repository.model.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, String> {
}
