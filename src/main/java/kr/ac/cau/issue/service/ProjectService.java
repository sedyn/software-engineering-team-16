package kr.ac.cau.issue.service;

import kr.ac.cau.issue.repository.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectService projectService;

    public List<Project> getProjects() {
        return projectService.getProjects();
    }
}
