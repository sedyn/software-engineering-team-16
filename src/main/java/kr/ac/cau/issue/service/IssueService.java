package kr.ac.cau.issue.service;

import kr.ac.cau.issue.repository.IssueRepository;
import kr.ac.cau.issue.repository.model.Issue;
import kr.ac.cau.issue.repository.model.SimpleIssue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IssueService {

    private final IssueRepository issueRepository;

    public List<SimpleIssue> getSimpleIssuesByProjectId(String projectId) {
        return issueRepository.findAllByProjectId(projectId);
    }

    public Optional<Issue> getIssue(int issueId) {
        return issueRepository.findById(issueId);
    }

}
