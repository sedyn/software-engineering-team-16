package kr.ac.cau.issue.service;

import kr.ac.cau.issue.controller.model.AddIssueRequest;
import kr.ac.cau.issue.repository.IssueRepository;
import kr.ac.cau.issue.repository.ProjectRepository;
import kr.ac.cau.issue.repository.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public void addIssue(User user, String projectId, AddIssueRequest request) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        Issue issue = new Issue();
        issue.setTitle(request.title());
        issue.setProject(project);
        issue.setReporter(user);
        issue.setStatus(request.status());
        issue.setPriority(request.priority());
        issue.setReportedAt(LocalDateTime.now());

        issueRepository.save(issue);
    }

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Map<String, Map<IssueStatus, Integer>> getIssueStatistic() {
        List<IssueStatisticRecord> issueStatisticRecords = issueRepository.getIssueStatistic();

        return issueStatisticRecords
                .stream()
                .reduce(
                        new HashMap<>(),
                        (acc, item) -> {
                            acc.compute(
                                    item.getDatetime().format(dateTimeFormatter),
                                    (k, v) -> {
                                        if (v == null) {
                                            v = new HashMap<>();
                                        }
                                        v.put(item.getStatus(), item.getIssueCount());
                                        return v;
                                    }
                            );

                            return acc;
                        },
                        (a, b) -> {
                            a.putAll(b);
                            return a;
                        });
    }
}
