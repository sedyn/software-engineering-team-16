package kr.ac.cau.issue.service;

import kr.ac.cau.issue.repository.ProjectRepository;
import kr.ac.cau.issue.repository.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Iterable<Project> getProjects() {
        return projectRepository.findAll();
    }

    public void addProject(String id, String description) {
        Project project = new Project();
        project.setId(id);
        project.setName(description);

        projectRepository.save(project);
    }
}
