package kr.ac.cau.issue.service;

import kr.ac.cau.issue.repository.ProjectRepository;
import kr.ac.cau.issue.repository.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Iterable<Project> getProjects() {
        return projectRepository.findAll();
    }
}
