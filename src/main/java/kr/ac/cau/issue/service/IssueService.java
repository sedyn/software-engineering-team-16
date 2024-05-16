package kr.ac.cau.issue.service;

import kr.ac.cau.issue.controller.model.AddIssueRequest;
import kr.ac.cau.issue.repository.IssueRepository;
import kr.ac.cau.issue.repository.ProjectRepository;
import kr.ac.cau.issue.repository.model.Issue;
import kr.ac.cau.issue.repository.model.Project;
import kr.ac.cau.issue.repository.model.SimpleIssue;
import kr.ac.cau.issue.repository.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;

    public List<SimpleIssue> getSimpleIssuesByProjectId(String projectId) {
        return issueRepository.findAllByProjectId(projectId);
    }

    public Optional<Issue> getIssue(int issueId) {
        return issueRepository.findById(issueId);
    }

    public void addIssue(String projectId, AddIssueRequest request) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        User user = new User();
        user.setId(1);

        Issue issue = new Issue();
        issue.setTitle(request.title());
        issue.setProject(project);
        issue.setReporter(user);
        issue.setStatus(request.status());
        issue.setPriority(request.priority());
        issue.setReportedAt(LocalDateTime.now());

        issueRepository.save(issue);
    }

}
